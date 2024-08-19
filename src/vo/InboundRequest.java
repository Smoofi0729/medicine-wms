package vo;

import lombok.Data;

@Data
public class InboundRequest {
  private String requestId; //입고요청ID
  private String requestDate; //입고요청날짜
  private String memberId; //입고요청 공급업체
  private String productId; //입고물풀ID
  //private String productName; //입고물품명
  private int quantity; //입고물품 수량
  private String inspectionStatus; //입고물품 검수상태
  //private String approvalStatus; // 입고물품 승인상태

  public InboundRequest(String mId, String pId, int q) { //입고요청 시(회원, Insert)
    memberId = mId;
    productId = pId;
    quantity = q;
  }
  public InboundRequest(String rId, String rDate, String mId, String pId, int q, String inStatus){
    // 입고요청 현황 출력 시
    requestId = rId;
    requestDate = rDate;
    memberId = mId;
    productId = pId;
    //productName = pdName;
    quantity = q;
    inspectionStatus = inStatus;
    //approvalStatus = appStatus;
  }

  public InboundRequest(String rId, String rDate, String mId, String pId, int q){
    // 입고요청 현황 출력 시
    requestId = rId;
    requestDate = rDate;
    memberId = mId;
    productId = pId;
    //productName = pdName;
    quantity = q;
  }
}
