package interfaces.release;

import interfaces.CrudService;
import java.sql.ResultSet;

public interface ReleaseRequestService {
  void releaseRequestMenuForMall();

  void releaseRequestMenuForManager();

  void readReleaseRequestByApprovalStatus();

  void readAllReleaseRequest();

  void readByReleaseRequestId();

  void readByCustId();
}
