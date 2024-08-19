package interfaces.release;

public interface ReleaseRequestService {
  void releaseRequestMenuForMall();

  void releaseRequestMenuForManager();

  void readReleaseRequestByApprovalStatus();

  void readAllReleaseRequest();

  void readByReleaseRequestId();

  void readByCustId();
}
