package interfaces;

import vo.InboundApproval;
import java.util.ArrayList;

public interface InboundApprovalService {
  public ArrayList<InboundApproval> getInboundApproval();
  public int updateInboundApproval(String requestId, String managerId, String approvalStatus);

}
