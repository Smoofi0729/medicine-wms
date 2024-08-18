package vo.release;

import config.enums.ApprovalStatus;
import lombok.Data;
import vo.Member;
import vo.Stock;

@Data
public class ReleaseRequest {

  private String requestId;
  private Member shoppingMall;
  private Stock productLotNo;
  private int quantity;
  private String custName;
  private String custAddress;
  private String custPhone;
  private String custRequirement;
  private ApprovalStatus requestStatus;
  private String note;
}
