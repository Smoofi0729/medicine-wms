package dao;

import static config.CommonUsable.inputInt;
import static config.CommonUsable.inputStr;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import vo.Warehouse;

public class WarehouseDao extends ObjectDBIO {

  public boolean registerWh(Warehouse warehouse) {
    // warehouse 테이블이 있다고 가정
    String query = "INSERT warehouse VALUES(?,?,?,?,?,?,?,?)";

    try {
      open();
      execute(query);
      getPstmt().setString(1, inputStr("창고id"));
      getPstmt().setString(2, inputStr("창고이름"));
      getPstmt().setString(3, inputStr("창고주소"));
      getPstmt().setString(4, inputStr("창고연락처"));
      getPstmt().setInt(5, inputInt("창고수용량"));
      getPstmt().setDate(6, Date.valueOf(LocalDate.now()));
      getPstmt().setString(7, inputStr("관리자"));
      getPstmt().setString(8, inputStr("비고"));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return true;
  }

  public String selectWh(Warehouse warehouse) {
    String query = "SELECT * from warehouse where id = " + warehouse.getId();
    execute(query);
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
