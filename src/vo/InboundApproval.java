package vo;

import lombok.Data;

@Data
public class InboundApproval {
  String requestId; //입고요청 ID
  String approvalDate; //승인날짜
  String approvalId; //승인 관리자ID
  String approvalStatus; //승인결과

  public InboundApproval(String rId, String avDate, String avID, String avStatus){
    requestId = rId;
    approvalDate = avDate;
    approvalId = avID;
    approvalStatus = avStatus;
  }
}
