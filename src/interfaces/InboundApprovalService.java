package interfaces;

import DTO.InboundApproval;
import java.util.ArrayList;

public interface InboundApprovalService {
  public ArrayList<InboundApproval> getInboundApproval();
  public void updateInboundApproval(String requestId);

}
