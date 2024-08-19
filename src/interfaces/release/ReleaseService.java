package interfaces.release;

import java.io.IOException;
import java.sql.SQLException;

public interface ReleaseService {

  void showReleaseMenuForManager(String memberId) throws SQLException, IOException;

  void showReleasesForMall();

  void readAllData(String table);

  void readDataById(String table);

  void readReleaseByDate();

  void readWayBillByDate();
}
