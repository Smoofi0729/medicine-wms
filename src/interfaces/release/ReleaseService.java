package interfaces.release;

import java.io.IOException;
import java.sql.SQLException;

public interface ReleaseService {

  void releaseMenuForManager(String memberId) throws SQLException, IOException;

  void showReleasesForMall(String memberId);

  void readAllData(String table);

  void readDataById(String table);

  void readReleaseByDate();

  void readWayBillByDate();
}
