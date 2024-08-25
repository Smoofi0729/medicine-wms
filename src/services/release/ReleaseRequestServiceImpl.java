package services.release;

import static config.UtilMethod.inputInt;
import static config.UtilMethod.inputStr;
import static config.UtilMethod.isValidId;
import static config.UtilMethod.recheckDelete;
import static config.UtilMethod.selectColumn;
import static enums.Messeges.*;

import enums.ApprovalStatus;
import dao.release.ReleaseRequestDao;
import interfaces.release.ReleaseRequestService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import lombok.Data;

@Data
public class ReleaseRequestServiceImpl implements ReleaseRequestService {

  private String table = "release_request";
  private ReleaseRequestDao releaseRequestDao;

  public ReleaseRequestServiceImpl() {
    this.releaseRequestDao = new ReleaseRequestDao();
  }

  @Override
  public void releaseRequestMenuForMall(String memberId) {
    while(true){
    printMessage(RR_MENU);
    printMessage(DEVIDER);
    System.out.println("나의 출고요청 목록");
    printMessage(DEVIDER);
    printInfo(releaseRequestDao.selectFilterBy("member_id", memberId));
    printMessage(DEVIDER);
    System.out.println("1. 출고요청 등록 | 2. 수정 및 삭제 요청 | 3. 요청승인여부 확인 | 4. 회원메뉴로");
    switch (inputInt("메뉴선택")) {
      case 1 -> releaseRequestDao.registerReleaseRequest(memberId);
      case 2 -> requestUpdateByMall();
      case 3 -> releaseRequestDao.checkRequestStatus(inputStr("확인할 출고요청ID"));
      case 4 ->{return;}
      default -> System.out.println("입력이 잘못되었습니다.");
    }
    }
  }

  public void requestUpdateByMall() {
    String id = inputStr(WHICH_ID.getDescription());
    ResultSet rs = releaseRequestDao.selectFilterBy("release_reqId", id);
    if (isValidId(releaseRequestDao.selectFilterBy("release_reqId", id))) {
      printInfo(rs);
      printMessage(UPDATE_OR_DELETE);
      switch (inputInt("메뉴선택")) {
        case 1:
          // 수정 요청 처리
          HashMap<String, String> updateRequest = new HashMap<>();

          String update = inputStr(UPDATE_HOW.getDescription());
          String updateValue = "수정요청 - " + update;
          updateRequest.put("release_req_note", updateValue);

          boolean requestSuccess = releaseRequestDao.updateReleaseRequest(updateRequest, id);
          if (requestSuccess) {
            System.out.println("수정요청 성공");
          } else {
            System.out.println("수정요청 실패");
          }
          break;

        case 2:
          // 삭제 요청 처리
          if (recheckDelete()) {
            HashMap<String, String> deleteRequest = new HashMap<>();
            String deleteValue = "삭제요청";
            deleteRequest.put("release_req_note", deleteValue);

            requestSuccess = releaseRequestDao.updateReleaseRequest(deleteRequest, id);
            if (requestSuccess) {
              System.out.println("삭제요청 성공");
            } else {
              System.out.println("삭제요청 실패");
            }
          } else {
            System.out.println("삭제 철회");
          }
          break;
      }
    }
  }

  public void showSelectMenu() {
    System.out.println("1. 전체조회 | 2. 개별조회 | 3. 처리상태별 조회 | 4. 주문자별조회 | 5. 쇼핑몰요청확인");

    switch (inputInt(SELECT_HOW.getDescription())) {
      case 1 -> readAllReleaseRequest();
      case 2 -> readByReleaseRequestId();
      case 3 -> readReleaseRequestByApprovalStatus();
      case 4 -> readByCustId();
      case 5 -> readRequestByMall();
      default -> printMessage(WRONG_INPUT);
    }
  }

  @Override
  public void readReleaseRequestByApprovalStatus() {
    printMessage(APPROVAL_STATUS);
    switch (inputInt(SELECT_HOW.getDescription())) {
      case 1 -> releaseRequestDao.selectRequestIdByStatus("처리중");
      case 2 -> releaseRequestDao.selectRequestIdByStatus("승인");
      case 3 -> releaseRequestDao.selectRequestIdByStatus("거절");
    }
  }

  @Override
  public void releaseRequestMenuForManager() {
    while (true) {

      printMessage(DEVIDER);
      printMessage(RR_MENU);
      printMessage(DEVIDER);
      System.out.println("1. 조회 | 2. 수정 및 삭제 | 3. 출고관리메뉴로");
      switch (inputInt("메뉴선택")) {
        case 1 -> showSelectMenu();
        case 2 -> showUpdateMenu();
        case 3 -> {return;}
        default -> printMessage(WRONG_INPUT);
      }
    }
  }

  @Override
  public void readAllReleaseRequest() {
    printInfo(releaseRequestDao.selectReleaseRequest());
  }

  @Override
  public void readByReleaseRequestId() {
    String id = inputStr("출고요청ID");
    printInfo(releaseRequestDao.selectFilterBy("release_reqId", id));
  }

  @Override
  public void readByCustId() {
    String custId = inputStr("주문회원ID");
    printInfo(releaseRequestDao.selectFilterBy("member_id", custId));
  }

  public void readRequestByMall() {
    String confirm = inputStr("확인할 요청 : 수정요청 or 삭제요청");
    ResultSet rs = releaseRequestDao.selectFilterByLike("release_req_note", confirm +"%");
    printInfo(rs);
    releaseRequestDao.close(rs);
  }

  public void showUpdateMenu() {
    String id = inputStr(WHICH_ID.getDescription());
    ResultSet rs = releaseRequestDao.selectFilterBy("release_reqId", id);
    if (isValidId(releaseRequestDao.selectFilterBy("release_reqId", id))) {
      printInfo(rs);
      releaseRequestDao.close(rs);
      printMessage(UPDATE_OR_DELETE);
      switch (inputInt("메뉴선택")) {
        case 1:

          HashMap<String, String> updates = new HashMap<>();
          printMessage(WHICH_COLUMN);
          System.out.println("1.출고요청ID 2.요청날짜 3.주문회원ID 4.출고물품ID 5.주문수량 6.수취인이름 7.수취인주소 8.수취인연락처 9.주문요청사항 10. 요청처리상태 11.비고");
          while (true) {
            int choice = inputInt("수정할 항목");
            String column = selectColumn(table).get(choice);
            if (choice==0) {
              break;
            }
            String update = inputStr(UPDATE_HOW.getDescription());
            updates.put(column, update);
          }
          boolean success = releaseRequestDao.updateReleaseRequest(updates, id);
          if (success) {
            printMessage(UPDATE_SUCCESS);
          } else {
            printMessage(UPDATE_CANCEL);
          }
          break;
        case 2:
          if (recheckDelete()) {
            releaseRequestDao.deleteReleaseRequest(id);
            printMessage(DELETE_SUCCESS);
          } else {
            printMessage(DELETE_CANCEL);
          }
      }
    }
  }

  public void printInfo(ResultSet rs) {
    StringBuilder result = new StringBuilder();
    result.append(
        "출고요청ID\t\t\t\t\t\t\t요청날짜\t\t\t\t\t\t\t\t주문회원ID\t\t출고물품ID\t\t주문수량\t\t수취인이름\t\t수취인주소\t\t수취인연락처\t\t주문요청사항\t\t요청처리상태\t\t비고\t\t\t\t\t\n");
    try {
      while (rs.next()) {
        result.append(releaseRequestDao.getRs().getString("release_reqId")).append("\t\t");
        result.append(releaseRequestDao.getRs().getString("release_req_date")).append("\t\t");
        result.append(releaseRequestDao.getRs().getString("member_id")).append("\t\t");
        result.append(releaseRequestDao.getRs().getString("product_lotno")).append("\t\t");
        result.append(releaseRequestDao.getRs().getString("order_quantity")).append("\t\t");
        result.append(releaseRequestDao.getRs().getString("customer_name")).append("\t\t");
        result.append(releaseRequestDao.getRs().getString("customer_address")).append("\t\t");
        result.append(releaseRequestDao.getRs().getString("customer_phone")).append("\t\t");
        result.append(releaseRequestDao.getRs().getString("customer_requirement")).append("\t\t");
        result.append(releaseRequestDao.getRs().getString("release_req_status")).append("\t\t");
        result.append(releaseRequestDao.getRs().getString("release_req_note")).append("\n");
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
      releaseRequestDao.close(releaseRequestDao.getPstmt());
      releaseRequestDao.close(releaseRequestDao.getConnection());
    }
    System.out.println(result);
  }
}
