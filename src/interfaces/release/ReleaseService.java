package interfaces.release;

import interfaces.Services;
import java.sql.ResultSet;

public interface ReleaseService extends Services {

  void releaseMenu();

  void showReleasesForMall();

  void readAllData(String table);

  void readDataById(String table);

  void readReleaseByDate();

  void readWayBillByDate();

  void printInfo(ResultSet rs);
}
