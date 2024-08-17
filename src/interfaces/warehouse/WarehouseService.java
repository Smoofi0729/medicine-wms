package interfaces.warehouse;

import interfaces.Services;
import java.sql.ResultSet;

public interface WarehouseService extends Services {
  void warehouseMenu();

  void readAllWh();

  void readByWhLocation();

  void readByWhId();

  void printInfo(ResultSet rs);
}
