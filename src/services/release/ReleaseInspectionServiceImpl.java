package services.release;

import static config.UtilMethod.inputStr;

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
@AllArgsConstructor
public class ReleaseInspectionServiceImpl implements ReleaseInspectionService {

  private ReleaseInspectionDao releaseInspectionDao;

  @Override
  public void releaseInspectionMenu() {
    while (true) {
      System.out.println("=====================================================================");
      System.out.println("출고검수 메뉴");
      System.out.println("=====================================================================");

      System.out.println("1. 조회 | 2. 수정");
      switch (UtilMethod.inputInt("메뉴선택")) {
        case 1 -> showSelectMenu();
        case 2 -> showUpdateMenu();
        default -> System.out.println("입력이 잘못되었습니다.");
      }
    }
  }

  public void showSelectMenu() {
    System.out.println("1. 전체조회 | 2. 개별조회 | 3. 처리상태별 조회");

    switch (UtilMethod.inputInt("조회방법을 선택해주세요")) {
      case 1 -> readAllReleaseInspection();
      case 2 -> readByReleaseInspectionId();
      case 3 -> showStatusMenu();
      default -> System.out.println("입력이 잘못되었습니다.");
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
    System.out.println("1. 처리중 | 2. 승인 | 3. 거절");
    switch (UtilMethod.inputInt("조회방법을 선택해주세요")) {
      case 1 -> releaseInspectionDao.checkInspectionResult(ApprovalStatus.ON_PROCESS);
      case 2 -> releaseInspectionDao.checkInspectionResult(ApprovalStatus.APPROVED);
      case 3 -> releaseInspectionDao.checkInspectionResult(ApprovalStatus.REJECTED);
    }
  }

  public void showUpdateMenu() {
    String id = inputStr("수정할 출고요청의 id를 입력하세요");
    printInfo(releaseInspectionDao.selectFilterBy("release_insptId", id));
    HashMap<String, String> updates = new HashMap<>();
    while (true) {
      String column = inputStr("수정할 항목을 입력해주세요 (없으면 exit 입력)");
      if (column.equals("exit")) {
        break;
      }
      String update = inputStr("수정할 내용을 입력해주세요");
      updates.put(column, update);
    }
    boolean success = releaseInspectionDao.updateReleaseInspection(updates, id);
    if (success) {
      System.out.println("업데이트 성공");
    } else {
      System.out.println("업데이트 실패");
    }
  }

  @Override
  public void printInfo(ResultSet rs) {
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
