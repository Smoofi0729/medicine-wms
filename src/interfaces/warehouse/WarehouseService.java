package interfaces.warehouse;

import java.io.IOException;
import java.sql.SQLException;

public interface WarehouseService {

  void warehouseMenu(String memberId) throws SQLException, IOException;

  void readAllWh();

  void readByWhLocation();

  void readByWhId();
}
