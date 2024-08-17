package interfaces.warehouse;

import java.sql.ResultSet;

public interface WarehouseService {
  void warehouseMenu();

  void readAllWh();

  void readByWhLocation();

  void readByWhId();

  void printWarehouseInfo(ResultSet rs);
}
