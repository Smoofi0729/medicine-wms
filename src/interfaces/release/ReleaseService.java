package interfaces.release;

public interface ReleaseService {

  void showReleaseMenuForManager();

  void showReleasesForMall();

  void readAllData(String table);

  void readDataById(String table);

  void readReleaseByDate();

  void readWayBillByDate();
}
