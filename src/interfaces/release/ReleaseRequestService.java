package interfaces.release;

public interface ReleaseRequestService {

  void releaseRequestMenuForManager();

  void releaseRequestMenuForMall(String memberId);

  void readReleaseRequestByApprovalStatus();

  void readAllReleaseRequest();

  void readByReleaseRequestId();

  void readByCustId();
}
