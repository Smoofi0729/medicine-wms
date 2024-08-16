package dao;

import static config.UtilMethod.inputInt;
import static config.UtilMethod.inputStr;

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

  public boolean registerWh() {

    String query = "INSERT warehouse VALUES(?,?,?,?,?,?,?,?,?)";

    try (Connection connection = open();
        PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, inputStr("창고id"));
      pstmt.setString(2, inputStr("창고이름"));
      pstmt.setString(3, inputStr("창고주소"));
      pstmt.setString(4, inputStr("창고연락처"));
      pstmt.setInt(5, inputInt("창고수용량"));
      pstmt.setInt(6, inputInt("현재가용량"));
      pstmt.setDate(7, Date.valueOf(LocalDate.now()));
      pstmt.setString(8, inputStr("관리자id"));
      pstmt.setString(9, inputStr("비고"));
      pstmt.executeUpdate();
      System.out.println("등록성공");
      return true;
    } catch (SQLException e) {
      throw new RuntimeException(e);
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
        query.setLength(query.length()-2);
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
