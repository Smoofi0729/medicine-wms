package services.release;

import static config.UtilMethod.inputInt;
import static config.UtilMethod.inputStr;
import static config.UtilMethod.isValidId;
import static config.UtilMethod.selectColumn;
import static config.enums.Messeges.*;

import config.UtilMethod;
import config.enums.ApprovalStatus;
import dao.release.ReleaseInspectionDao;
import interfaces.release.ReleaseInspectionService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ReleaseInspectionServiceImpl implements ReleaseInspectionService {

  private String table = "release_inpsection";
  private ReleaseInspectionDao releaseInspectionDao;

  public ReleaseInspectionServiceImpl() {
    this.releaseInspectionDao = new ReleaseInspectionDao();
  }

  @Override
  public void releaseInspectionMenu() {
    while (true) {

      printMessage(DEVIDER);
      printMessage(RI_MENU);
      printMessage(DEVIDER);
      System.out.println("1. 조회 | 2. 수정");
      switch (UtilMethod.inputInt("메뉴선택")) {
        case 1 -> showSelectMenu();
        case 2 -> showUpdateMenu();
        default -> printMessage(WRONG_INPUT);
      }
    }
  }

  public void showSelectMenu() {
    System.out.println("1. 전체조회 | 2. 개별조회 | 3. 처리상태별 조회");

    switch (inputInt(SELECT_HOW.getDescription())) {
      case 1 -> readAllReleaseInspection();
      case 2 -> readByReleaseInspectionId();
      case 3 -> showStatusMenu();
      default -> printMessage(WRONG_INPUT);
    }
  }

  @Override
  public void readAllReleaseInspection() {
    printInfo(releaseInspectionDao.selectReleaseInspection());
  }

  @Override
  public void readByReleaseInspectionId() {
    String id = inputStr("출고검수ID");
    printInfo(releaseInspectionDao.selectFilterBy("release_insptId", id));
  }

  @Override
  public void showStatusMenu() {
    printMessage(APPROVAL_STATUS);
    switch (inputInt(SELECT_HOW.getDescription())) {
      case 1 -> releaseInspectionDao.checkInspectionResult(ApprovalStatus.ON_PROCESS);
      case 2 -> releaseInspectionDao.checkInspectionResult(ApprovalStatus.APPROVED);
      case 3 -> releaseInspectionDao.checkInspectionResult(ApprovalStatus.REJECTED);
    }
  }

  public void showUpdateMenu() {

    String id = inputStr(WHICH_ID.getDescription());
    ResultSet rs = releaseInspectionDao.selectFilterBy("release_insptId", id);
    if (isValidId(releaseInspectionDao.selectFilterBy("release_insptId", id))) {
      printInfo(rs);
      HashMap<String, String> updates = new HashMap<>();
      printMessage(WHICH_COLUMN);
      System.out.println("1.출고검수ID 2.출고요청ID 3.검수날짜 4.검수자 5.검수시간 6.비고");
      while (true) {
        int choice = inputInt("수정할 항목");
        String column = selectColumn(table).get(choice);
        if (choice == 0) {
          break;
        }
        String update = inputStr(UPDATE_HOW.getDescription());
        updates.put(column, update);
      }

      boolean success = releaseInspectionDao.updateReleaseInspection(updates, id);
      if (success) {
        printMessage(UPDATE_SUCCESS);
      } else {
        printMessage(UPDATE_CANCEL);
      }
    }
  }

    public void printInfo(ResultSet rs){
      StringBuilder result = new StringBuilder();
      result.append(
          "출고검수ID\t\t\t출고요청ID\t\t\t검수날짜\t\t\t검수자\t\t\t검수시간\t\t\t비고\n");
      try {
        while (rs.next()) {
          result.append(releaseInspectionDao.getRs().getString("release_insptId")).append("\t\t");
          result.append(releaseInspectionDao.getRs().getString("release_reqId")).append("\t\t");
          result.append(releaseInspectionDao.getRs().getString("inspection_result")).append("\t\t");
          result.append(releaseInspectionDao.getRs().getString("member_id")).append("\t\t");
          result.append(releaseInspectionDao.getRs().getString("insepction_time")).append("\t\t");
          result.append(releaseInspectionDao.getRs().getString("inspection_note")).append("\t\t");
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      } finally {
        try {
          if (rs != null) {
            rs.close();
          }
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
        releaseInspectionDao.close(releaseInspectionDao.getPstmt());
        releaseInspectionDao.close(releaseInspectionDao.getConnection());
      }
      System.out.println(result);
    }
  }

