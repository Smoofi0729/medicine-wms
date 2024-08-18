package interfaces.release;

import interfaces.Services;
import java.sql.ResultSet;

public interface ReleaseRequestService extends Services {
  void releaseRequestMenuForMall();

  void releaseRequestMenuForManager();

  void readReleaseRequestByApprovalStatus();

  void readAllReleaseRequest();

  void readByReleaseRequestId();

  void readByCustId();

  void printInfo(ResultSet rs);
}
