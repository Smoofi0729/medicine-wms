package services.release;

import static config.UtilMethod.inputInt;
import static config.UtilMethod.inputStr;
import static config.UtilMethod.isValidId;
import static config.UtilMethod.recheckDelete;

import config.UtilMethod;
import config.enums.ApprovalStatus;
import dao.release.ReleaseDao;
import interfaces.release.ReleaseService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReleaseServiceImpl implements ReleaseService {

  private ReleaseDao releaseDao;

  @Override
  public void releaseMenu() {
    while (true) {
      System.out.println("=====================================================================");
      System.out.println("출고관리 메뉴");
      System.out.println("=====================================================================");

      System.out.println("1. 출고조회 | 2. 출고수정 및 삭제 | 3. 배차조회 | 4. 배차수정 | 5. 운송장조회 | 6. 운송장수정");
      switch (UtilMethod.inputInt("메뉴선택")) {
        case 1 -> showReleaseMenu();
        case 2 -> showUpdateMenu("releases");
        case 3 -> showDispatchMenu();
        case 4 -> showUpdateMenu("dispatch");
        case 5 -> showWayBillMenu();
        case 6 -> showUpdateMenu("waybill");
        default -> System.out.println("입력이 잘못되었습니다.");
      }
    }
  }

  @Override
  public void showReleasesForMall() {
    System.out.println("나의 출고현황");
    releaseDao.callMyReleaseProc(inputStr("회원ID"));
  }

  public void showReleaseMenu() {
    String table = "releases";
    System.out.println("1. 전체조회 | 2. 개별조회 | 3. 출고날짜별 조회 | 4. 상위메뉴로");
    switch (UtilMethod.inputInt("조회방법을 선택해주세요")) {
      case 1 -> readAllData(table);
      case 2 -> readDataById(table);
      case 3 -> readReleaseByDate();
      case 4 -> releaseMenu();
    }
  }

  public void showDispatchMenu() {
    String table = "dispatch";
    System.out.println("1. 전체조회 | 2. 개별조회 | 3. 등록대기중배차 조회 | 4. 상위메뉴로");
    switch (UtilMethod.inputInt("조회방법을 선택해주세요")) {
      case 1 -> readAllData(table);
      case 2 -> readDataById(table);
      case 3 -> releaseDao.readDispatchByStatus(ApprovalStatus.ON_PROCESS);
      case 4 -> releaseMenu();
    }
  }

  public void showWayBillMenu() {
    String table = "waybill";
    System.out.println("1. 전체조회 | 2. 개별조회 | 3. 배송출발날짜별 조회 | 4. 상위메뉴로");
    switch (UtilMethod.inputInt("조회방법을 선택해주세요")) {
      case 1 -> readAllData(table);
      case 2 -> readDataById(table);
      case 3 -> readWayBillByDate();
      case 4 -> releaseMenu();
    }
  }

  @Override
  public void readAllData(String table) {
    printInfo(releaseDao.selectData(table));
  }

  @Override
  public void readDataById(String table) {
    String id = inputStr("출고ID");
    printInfo(releaseDao.selectFilterBy(table, table + "_id", id));
  }

  @Override
  public void readReleaseByDate() {
    String releaseDate = inputStr("출고날짜");
    printInfo(releaseDao.selectFilterBy("releases", "releases_date", releaseDate));
  }

  @Override
  public void readWayBillByDate() {
    String departureDate = inputStr("배송출발날짜");
    printInfo(releaseDao.selectFilterBy("waybill", "departure_date", departureDate));
  }

  public void showUpdateMenu(String table) {
    String id = inputStr("수정 및 삭제할 ID를 입력하세요");
    ResultSet rs = releaseDao.selectFilterBy(table, table + "_id", id);
    if (isValidId(releaseDao.selectFilterBy(table, table + "_id", id))) {
      printInfo(rs);
      releaseDao.close(rs);
      System.out.println("1. 수정 | 2. 삭제");
      switch (inputInt("메뉴선택")) {
        case 1:
          HashMap<String, String> updates = new HashMap<>();
          while (true) {
            String column = inputStr("수정할 항목을 입력해주세요 (없으면 exit 입력)");
            if (column.equals("exit")) {
              break;
            }
            String update = inputStr("수정할 내용을 입력해주세요");
            updates.put(column, update);
          }
          boolean success = releaseDao.updateData(table, updates, id);
          if (success) {
            System.out.println("업데이트 성공");
          } else {
            System.out.println("업데이트 실패");
          }
          break;
        case 2:
          if (recheckDelete()) {
            releaseDao.deleteRelease(id);
            System.out.println("삭제 성공");
          } else {
            System.out.println("삭제 철회");
          }
      }
    }
  }


  @Override
  public void printInfo(ResultSet rs) {
    StringBuilder result = new StringBuilder();
    result.append("출고ID\t\t\t출고요청ID\t\t\t배차ID\t\t\t\t\t\t출고날짜\t\t\t출고담당자\t\t\t비고\n");
    try {
      while (rs.next()) {
        result.append(releaseDao.getRs().getString("release_id")).append("\t\t");
        result.append(releaseDao.getRs().getString("release_reqId")).append("\t\t");
        result.append(releaseDao.getRs().getString("dispatch_id")).append("\t\t");
        result.append(releaseDao.getRs().getString("release_date")).append("\t\t");
        result.append(releaseDao.getRs().getString("member_id")).append("\t\t");
        result.append(releaseDao.getRs().getString("release_note")).append("\t\t");
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
      releaseDao.close(releaseDao.getPstmt());
      releaseDao.close(releaseDao.getConnection());
    }
    System.out.println(result);
  }
}
