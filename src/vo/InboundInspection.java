package vo;

import lombok.Data;

@Data
public class InboundInspection {
  String requestId; //입고요청 ID
  String inspectionDate; //검수날짜
  String inspectorId; //검수 관리자ID
  String inspectionStatus; //검수결과

  public InboundInspection(String rId, String ipDate, String ipID, String ipStatus){
    requestId = rId;
    inspectionDate = ipDate;
    inspectorId = ipID;
    inspectionStatus = ipStatus;
  }

}
