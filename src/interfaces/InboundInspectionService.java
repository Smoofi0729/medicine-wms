package interfaces;

import vo.InboundInspection;
import java.util.ArrayList;

public interface InboundInspectionService{
  public ArrayList<InboundInspection> getInboundInspection();
  public int updateInboundInspection(String requestId, String InspectionStatus);
}
