package interfaces.warehouse;

import interfaces.CrudService;
import java.sql.ResultSet;

public interface WarehouseService {
  void warehouseMenu();

  void readAllWh();

  void readByWhLocation();

  void readByWhId();
}
