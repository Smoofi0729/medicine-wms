package services;

import dao.warehouse.WarehouseDao;
import dto.warehouse.Warehouse;
import java.util.ArrayList;
import vo.Warehouse;

public class WarehouseServiceImpl {

  private WarehouseDao warehouseDao;
  private static ArrayList<Warehouse> warehouseList = new ArrayList<>();

  public ArrayList<Warehouse> addWarehouseToList(Warehouse warehouse) {
    Warehouse.getInstance().add(warehouse);
    return Warehouse.getInstance();
  }

  public String readAllWh() {
    return Warehouse.getInstance().toString();
  }

  public ArrayList<Warehouse> readByWhLocation() {
    warehouseDao.selectWh();
    return null;
  }

  public ArrayList<Warehouse> readByWhType() {
    return null;
  }

  public ArrayList<Warehouse> readByWhName() {
    return null;
  }
}
