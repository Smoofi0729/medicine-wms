package dao.warehouse;

import static config.UtilMethod.inputInt;
import static config.UtilMethod.inputStr;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import lombok.Data;

@Data
public class WarehouseDao extends WarehouseDBIO {

  public void registerWh() {

    String procedure = "{CALL registerWarehouse(?,?,?,?,?,?,?,?,?)}";
    boolean success = false;
    while (!success) {
      try (Connection connection = open();
          CallableStatement cstmt = connection.prepareCall(procedure)) {

        cstmt.setString(1, inputStr("창고id"));
        cstmt.setString(2, inputStr("창고이름"));
        cstmt.setString(3, inputStr("창고주소"));
        cstmt.setString(4, inputStr("창고연락처"));
        int capacity = inputInt("창고수용량");
        cstmt.setInt(5, capacity);
        cstmt.setInt(6, capacity);
        cstmt.setDate(7, Date.valueOf(LocalDate.now()));
        cstmt.setString(8, inputStr("관리자id"));
        cstmt.setString(9, inputStr("비고"));
        cstmt.executeUpdate();
        System.out.println("등록성공");
        success = true;
      } catch (SQLException e) {
        System.out.println(e.getMessage() + "다시 입력해주세요.");
        }
      }
    }


  public ResultSet selectWh() {
    String query = "SELECT * FROM warehouse";
    try {
      open();
      readyPstmt(query);
      setRs(getPstmt().executeQuery());
      return getRs();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public ResultSet selectFilterBy(String column, String value) {
    String query = "SELECT * FROM warehouse WHERE " + column + " = ?";
    try {
      open();
      readyPstmt(query);
      getPstmt().setString(1, value);
      setRs(getPstmt().executeQuery());
      return getRs();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public int checkCapacity(String id) {
    String query = "SELECT warehouse_available FROM warehouse where warehouse_id = ?";
    try {
      open();
      readyPstmt(query);
      getPstmt().setString(1, id);
      setRs(getPstmt().executeQuery());
      if (getRs().next()) {
        return getRs().getInt("warehouse_available");
      } else {
        System.out.println("창고ID가 존재하지 않습니다.");
        return -1;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean updateWh(HashMap<String, String> columns, String id) {
    StringBuilder query = new StringBuilder()
        .append("UPDATE warehouse SET ");
    for (String column : columns.keySet()) {
      query.append(column).append(" = ?, ");
    }
    query.setLength(query.length() - 2);
    query.append(" where warehouse_id = ?");

    try {
      open();
      readyPstmt(query.toString());
      int index = 1;
      for (String column : columns.values()) {
        getPstmt().setString(index++, column);
      }
      getPstmt().setString(index, id);
      getPstmt().executeUpdate();
      return true;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(getPstmt());
      close(getConnection());
    }
  }

  public boolean deleteWh(String value) {
    String query = "DELETE FROM warehouse WHERE warehouse_id = ?";
    try {
      open();
      readyPstmt(query);
      getPstmt().setString(1, value);
      getPstmt().executeUpdate();
      return true;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
