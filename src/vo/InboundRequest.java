package vo;

import lombok.Data;

@Data
public class InboundRequest {
  private String reqeustId; //입고요청ID
  private String requestDate; //입고요청날짜
  private String memberId; //입고요청 공급업체
  public InboundRequest(String rId, String rDate, String mId){
    reqeustId = rId;
    requestDate = rDate;
    memberId = mId;
  }
}
