package dao;

import dao.ObjectDBIO;
import dto.warehouse.Warehouse;
import java.util.ArrayList;

public class WarehouseDao extends ObjectDBIO {

  public boolean registerWh(Warehouse warehouse) {
    String query = "INSERT " + warehouse + " VALUES(null, ?,?,?,?,?,?)";
    return true;
  }

  public String selectWh(ArrayList<Warehouse> warehouseList, Warehouse warehouse) {
    String query = "SELECT * from " + warehouseList + " where id = " + warehouse.getId();
    executeQuery(query);
    return warehouse.toString();
  }

  public boolean updateWh() {
    return false;
  }

  public boolean deleteWh() {
    return false;
  }

  public void filterBy() {
    String query = ""
  }
}
