package services.release;

import static config.UtilMethod.inputInt;
import static config.UtilMethod.inputStr;
import static config.UtilMethod.isValidId;
import static config.UtilMethod.selectColumn;
import static enums.Messeges.APPROVAL_STATUS;
import static enums.Messeges.DEVIDER;
import static enums.Messeges.RI_MENU;
import static enums.Messeges.SELECT_HOW;
import static enums.Messeges.UPDATE_CANCEL;
import static enums.Messeges.UPDATE_HOW;
import static enums.Messeges.UPDATE_SUCCESS;
import static enums.Messeges.WHICH_COLUMN;
import static enums.Messeges.WHICH_ID;
import static enums.Messeges.WRONG_INPUT;
import static enums.Messeges.printMessage;

import config.UtilMethod;
import dao.release.ReleaseInspectionDao;
import enums.ApprovalStatus;
import interfaces.release.ReleaseInspectionService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import lombok.Data;

@Data
public class ReleaseInspectionServiceImpl implements ReleaseInspectionService {

  private String table = "release_inspection";
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
      System.out.println("1. 조회 | 2. 수정 | 3. 출고관리메뉴로");
      switch (UtilMethod.inputInt("메뉴선택")) {
        case 1 -> showSelectMenu();
        case 2 -> showUpdateMenu();
        case 3 -> {
          return;
        }
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
      case 1 ->
          releaseInspectionDao.checkInspectionResult(String.valueOf(ApprovalStatus.승인대기중));
      case 2 ->
          releaseInspectionDao.checkInspectionResult(String.valueOf(ApprovalStatus.승인));
      case 3 ->
          releaseInspectionDao.checkInspectionResult(String.valueOf(ApprovalStatus.거부));
    }
  }

  public void showUpdateMenu() {
    printMessage(DEVIDER);
    System.out.println("검수ID현황");
    printMessage(DEVIDER);
    readAllReleaseInspection();
    String id = inputStr(WHICH_ID.getDescription());
    if (isValidId(releaseInspectionDao.selectFilterBy("release_insptId", id))) {
      printInfo(releaseInspectionDao.selectFilterBy("release_insptId", id));
      HashMap<String, String> updates = new HashMap<>();
      printMessage(WHICH_COLUMN);
      System.out.println("1.출고검수ID 2.출고요청ID 3.검수결과 4.검수자 5.검수시간 6.비고");
      while (true) {
        int choice = inputInt("수정할 항목");
        String column = selectColumn(table).get(choice);
//        if (column == null) {
//          System.out.println("잘못 선택하셨습니다. 다시 입력하세요");
//          continue;
//        }
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

  public void printInfo(ResultSet rs) {
    StringBuilder result = new StringBuilder();
    result.append(
        "출고검수ID\t\t\t\t\t\t\t출고요청ID\t\t\t\t\t\t\t검수결과\t\t\t검수자\t\t\t\t검수시간\t\t\t비고\n");
    try {
      while (rs.next()) {
        result.append(releaseInspectionDao.getRs().getString("release_insptId")).append("\t\t");
        result.append(releaseInspectionDao.getRs().getString("release_reqId")).append("\t\t");
        result.append(releaseInspectionDao.getRs().getString("inspection_result")).append("\t\t");
        result.append(releaseInspectionDao.getRs().getString("member_id")).append("\t\t");
        String inspectionTime = releaseInspectionDao.getRs().getString("inspection_time");
        if (inspectionTime == null) {
          inspectionTime = "";
        }
        result.append(inspectionTime).append("\t\t");
        result.append(releaseInspectionDao.getRs().getString("inspection_note")).append("\n");
      }
    } catch (SQLException e) {
      e.getMessage();
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

