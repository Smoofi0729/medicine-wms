package services.release;

import static config.UtilMethod.inputInt;
import static config.UtilMethod.inputStr;
import static config.UtilMethod.isValidId;
import static config.UtilMethod.recheckDelete;
import static config.UtilMethod.selectColumn;
import static enums.Messeges.*;

import config.UtilMethod;
import controller.CLIController;
import enums.ApprovalStatus;
import dao.release.ReleaseDao;
import interfaces.release.ReleaseService;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import lombok.Data;

@Data
public class ReleaseServiceImpl implements ReleaseService {

  private ReleaseDao releaseDao;
  private CLIController cliController;

  public ReleaseServiceImpl() {
    this.releaseDao = new ReleaseDao();
    this.cliController = new CLIController();
  }

  @Override
  public void showReleaseMenuForManager(String memberId) throws SQLException, IOException {
    while (true) {

      printMessage(DEVIDER);
      printMessage(RL_MENU);
      printMessage(DEVIDER);
      System.out.println("1. 출고조회 | 2. 출고수정 및 삭제 | 3. 배차조회 | 4. 배차수정 | 5. 운송장조회 | 6. 운송장수정 | 7. 관리자메뉴로");
      switch (UtilMethod.inputInt("메뉴선택")) {
        case 1 -> showReadReleaseMenu();
        case 2 -> showUpdateMenu("releases");
        case 3 -> showReadDispatchMenu();
        case 4 -> showUpdateMenu("dispatch");
        case 5 -> showReadWayBillMenu();
        case 6 -> showUpdateMenu("waybill");
        case 7 -> cliController.adminMainMenu(memberId);
        default -> printMessage(WRONG_INPUT);
      }
    }
  }

  @Override
  public void showReleasesForMall() {
    System.out.println("나의 출고현황");
    releaseDao.callMyReleaseProc(inputStr("회원ID"));
  }

  public void showReadReleaseMenu() {
    String table = "releases";
    System.out.println("1. 전체조회 | 2. 개별조회 | 3. 출고날짜별 조회 | 4. 상위메뉴로");
    switch (UtilMethod.inputInt(SELECT_HOW.getDescription())) {
      case 1 -> readAllData(table);
      case 2 -> readDataById(table);
      case 3 -> readReleaseByDate();
      case 4 -> showReadReleaseMenu();
    }
  }

  public void showReadDispatchMenu() {
    String table = "dispatch";
    System.out.println("1. 전체조회 | 2. 개별조회 | 3. 등록대기중배차 조회 | 4. 상위메뉴로");
    switch (UtilMethod.inputInt(SELECT_HOW.getDescription())) {
      case 1 -> readAllData(table);
      case 2 -> readDataById(table);
      case 3 -> releaseDao.readDispatchByStatus(ApprovalStatus.ON_PROCESS);
      case 4 -> showReadReleaseMenu();
    }
  }

  public void showReadWayBillMenu() {
    String table = "waybill";
    System.out.println("1. 전체조회 | 2. 개별조회 | 3. 배송출발날짜별 조회 | 4. 상위메뉴로");
    switch (UtilMethod.inputInt(SELECT_HOW.getDescription())) {
      case 1 -> readAllData(table);
      case 2 -> readDataById(table);
      case 3 -> readWayBillByDate();
      case 4 -> showReadReleaseMenu();
    }
  }

  @Override
  public void readAllData(String table) {
    if (table.equals("releases")) {
      printReleaseInfo(releaseDao.selectData(table));;
    } else if (table.equals("dispatch")) {
      printDispatchInfo(releaseDao.selectData(table));
    } else if (table.equals("waybill")) {
      printWayBillInfo(releaseDao.selectData(table));
    }
  }

  @Override
  public void readDataById(String table) {
    String id = null;
    if (table.equals("releases")) {
      id = inputStr("출고ID");
      printReleaseInfo(releaseDao.selectFilterBy(table, table + "_id", id));;
    } else if (table.equals("dispatch")) {
      id = inputStr("배차ID");
      printDispatchInfo(releaseDao.selectFilterBy(table, table + "_id", id));;
    } else if (table.equals("waybill")) {
      id = inputStr("운송장ID");
      printWayBillInfo(releaseDao.selectFilterBy(table, table + "_id", id));;
    }
  }

  @Override
  public void readReleaseByDate() {
    String releaseDate = inputStr("출고날짜");
    printReleaseInfo(releaseDao.selectFilterBy("releases", "releases_date", releaseDate));
  }

  @Override
  public void readWayBillByDate() {
    String departureDate = inputStr("배송출발날짜");
    printWayBillInfo(releaseDao.selectFilterBy("waybill", "departure_date", departureDate));
  }

  public void showUpdateMenu(String table) {
    String id = inputStr(WHICH_ID.getDescription());
    ResultSet rs = releaseDao.selectFilterBy(table, table + "_id", id);
    if (isValidId(releaseDao.selectFilterBy(table, table + "_id", id))) {
      if (table.equals("releases")) {
        printReleaseInfo(rs);;
      } else if (table.equals("dispatch")) {
        printDispatchInfo(rs);
      } else if (table.equals("waybill")) {
        printWayBillInfo(rs);
      }
      releaseDao.close(rs);
      printMessage(UPDATE_OR_DELETE);
      switch (inputInt("메뉴선택")) {
        case 1:
          HashMap<String, String> updates = new HashMap<>();
          printMessage(WHICH_COLUMN);
          if (table.equals("releases")) {
            System.out.println("1.출고ID 2.출고요청ID 3.배차ID 4.출고날짜 5.출고담당자 6.비고");
          } else if (table.equals("dispatch")) {
            System.out.println("1.배차ID 2.차량ID 3.배차상태 4.배송기사ID 5.배차등록날짜 6.비고");
          } else if (table.equals("waybill")) {
            System.out.println("1.운송장ID 2.출고ID 5.배송상태 6.배송출발날짜 7.비고");
          }
          while (true) {
            int choice = inputInt("수정할 항목");
            String column = selectColumn(table).get(choice);
            if (choice==0) {
              break;
            }
            String update = inputStr(UPDATE_HOW.getDescription());
            updates.put(column, update);
          }
          boolean success = releaseDao.updateData(table, updates, id);
          if (success) {
            printMessage(UPDATE_SUCCESS);
          } else {
            printMessage(UPDATE_CANCEL);
          }
          break;
        case 2:
          if (recheckDelete()) {
            releaseDao.deleteRelease(id);
            printMessage(DELETE_SUCCESS);
          } else {
            printMessage(DELETE_CANCEL);
          }
      }
    }
  }

  public void printReleaseInfo(ResultSet rs) {
    StringBuilder result = new StringBuilder();
    result.append("출고ID\t\t\t출고요청ID\t\t\t배차ID\t\t\t출고날짜\t\t\t출고담당자\t\t\t비고\n");
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

  public void printDispatchInfo(ResultSet rs) {
    StringBuilder result = new StringBuilder();
    result.append("배차ID\t\t\t차량ID\t\t\t배차상태\t\t\t배송기사ID\t\t\t배차등록날짜\t\t\t비고\n");
    try {
      while (rs.next()) {
        result.append(releaseDao.getRs().getString("dispatch_id")).append("\t\t");
        result.append(releaseDao.getRs().getString("vehicle_id")).append("\t\t");
        result.append(releaseDao.getRs().getString("dispatch_status")).append("\t\t");
        result.append(releaseDao.getRs().getString("member_id")).append("\t\t");
        result.append(releaseDao.getRs().getString("dispatch_regiDate")).append("\t\t");
        result.append(releaseDao.getRs().getString("dispatch_note")).append("\t\t");
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

  public void printWayBillInfo(ResultSet rs) {
    StringBuilder result = new StringBuilder();
    result.append("운송장ID\t\t\t출고ID\t\t\t배송상태\t\t\t배송출발날짜\t\t\t비고\n");
    try {
      while (rs.next()) {
        result.append(releaseDao.getRs().getString("waybill_id")).append("\t\t");
        result.append(releaseDao.getRs().getString("releases_id")).append("\t\t");
        result.append(releaseDao.getRs().getString("delivery_status")).append("\t\t");
        result.append(releaseDao.getRs().getString("departure_date")).append("\t\t");
        result.append(releaseDao.getRs().getString("waybill_note")).append("\t\t");
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
