package services.release;

import static config.UtilMethod.inputInt;
import static config.UtilMethod.inputStr;
import static config.UtilMethod.isValidId;
import static config.UtilMethod.recheckDelete;

import config.UtilMethod;
import config.enums.ApprovalStatus;
import dao.release.ReleaseRequestDao;
import interfaces.release.ReleaseRequestService;
import interfaces.warehouse.SectionService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReleaseRequestServiceImpl implements ReleaseRequestService {

  private ReleaseRequestDao releaseRequestDao;

  @Override
  public void releaseRequestMenuForMall() {
    System.out.println("출고요청 메뉴");
    System.out.println("1. 보유재고조회 | 2. 출고요청 | 3. 수정 및 삭제 요청 | 4. 요청처리상태 확인");
    switch (inputInt("메뉴선택")) {
      case 1 -> showSelectMenu();
      case 2 -> releaseRequestDao.registerReleaseRequest();
      case 3 -> requestUpdate();
      case 4 -> System.out.println(
          "처리상태 : " + releaseRequestDao.checkRequestStatus(inputStr("확인할 출고요청ID")));
      default -> System.out.println("입력이 잘못되었습니다.");
    }
  }

  public void requestUpdate() {
    String id = inputStr("수정 및 삭제할 출고요청의 id를 입력하세요");
    ResultSet rs = releaseRequestDao.selectFilterBy("section_id", id);
    if (isValidId(releaseRequestDao.selectFilterBy("section_id", id))) {
      printInfo(rs);
      System.out.println("1. 수정 | 2. 삭제");
      switch (inputInt("메뉴선택")) {
        case 1:
          // 수정 요청 처리
          HashMap<String, String> updateRequest = new HashMap<>();

          String update = inputStr("수정할 내용을 입력해주세요");
          String updateValue = "수정요청 - " + update;
          updateRequest.put("release_request_note", updateValue);

          boolean success = releaseRequestDao.updateReleaseRequest(updateRequest, id);
          if (success) {
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
            deleteRequest.put("release_request_note", deleteValue);

            boolean deleteSuccess = releaseRequestDao.updateReleaseRequest(deleteRequest, id);
            if (deleteSuccess) {
              System.out.println("삭제 성공");
            } else {
              System.out.println("삭제 실패");
            }
          } else {
            System.out.println("삭제 철회");
          }
          break;
      }
    }
  }


  @Override
  public void releaseRequestMenuForManager() {
    while (true) {
      System.out.println("=====================================================================");
      System.out.println("출고요청관리 메뉴");
      System.out.println("=====================================================================");

      System.out.println("1. 조회 | 2. 수정 및 삭제");
      switch (inputInt("메뉴선택")) {
        case 1 -> showSelectMenu();
        case 2 -> showUpdateMenu();
        default -> System.out.println("입력이 잘못되었습니다.");
      }
    }
  }

  public void showSelectMenu() {
    System.out.println("1. 전체조회 | 2. 개별조회 | 3. 처리상태별 조회 | 4. 주문자별조회 | 5. 쇼핑몰요청확인");

    switch (inputInt("조회방법을 선택해주세요")) {
      case 1 -> readAllReleaseRequest();
      case 2 -> readByReleaseRequestId();
      case 3 -> readReleaseRequestByApprovalStatus();
      case 4 -> readByCustId();
      case 5 -> readRequestByMall();
      default -> System.out.println("입력이 잘못되었습니다.");
    }
  }

  @Override
  public void readReleaseRequestByApprovalStatus() {
    System.out.println("1. 처리중 | 2. 승인 | 3. 거절");
    switch (inputInt("조회방법을 선택해주세요")) {
      case 1 -> releaseRequestDao.selectRequestIdByStatus(ApprovalStatus.ON_PROCESS);
      case 2 -> releaseRequestDao.selectRequestIdByStatus(ApprovalStatus.APPROVED);
      case 3 -> releaseRequestDao.selectRequestIdByStatus(ApprovalStatus.REJECTED);
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
    String custName = inputStr("주문자이름");
    printInfo(releaseRequestDao.selectFilterBy("customer_name", custName));
  }

  public void readRequestByMall() {
    String confirm = inputStr("확인할 요청 : 수정요청 or 삭제요청");
    ResultSet rs = releaseRequestDao.selectFilterByLike("release_request_note", confirm +"%");
    printInfo(rs);
    releaseRequestDao.close(rs);
  }

  public void showUpdateMenu() {
    String id = inputStr("수정 및 삭제할 출고요청의 id를 입력하세요");
    ResultSet rs = releaseRequestDao.selectFilterBy("section_id", id);
    if (isValidId(releaseRequestDao.selectFilterBy("section_id", id))) {
      printInfo(rs);
      releaseRequestDao.close(rs);
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
          boolean success = releaseRequestDao.updateReleaseRequest(updates, id);
          if (success) {
            System.out.println("업데이트 성공");
          } else {
            System.out.println("업데이트 실패");
          }
          break;
        case 2:
          if (recheckDelete()) {
            releaseRequestDao.deleteReleaseRequest(id);
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
    result.append(
        "출고요청ID\t\t\t요청날짜\t\t\t주문회원ID\t\t\t출고물품ID\t\t\t주문수량\t\t\t수취인이름\t\t\t수취인주소\t\t\t수취인연락처\t\t\t주문요청사항\t\t\t요청처리상태비고\t\t\t\n");
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
        result.append(releaseRequestDao.getRs().getString("release_req_note")).append("\t\t");
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
