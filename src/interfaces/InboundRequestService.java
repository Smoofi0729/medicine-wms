package interfaces;

import vo.InboundRequest;
import java.util.ArrayList;

public interface InboundRequestService {
  public void addInboundRequest(InboundRequest inboundRequest);
  public ArrayList<InboundRequest> getInboundRequestForMember(String memberID);
  public ArrayList<InboundRequest> getInboundRequestForManager();
}
