package vo;

import lombok.Data;

@Data
public class InboundApproval {
  String requestId; //입고요청 ID
  String approvalDate; //승인날짜
  String approverId; //승인 관리자ID
  String approvalStatus; //승인결과

  public InboundApproval(String rId, String avDate, String avID, String avStatus){
    requestId = rId;
    approvalDate = avDate;
    approverId = avID;
    approvalStatus = avStatus;
  }
}
