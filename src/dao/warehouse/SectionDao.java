package dao.warehouse;

import static config.UtilMethod.inputStr;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SectionDao extends WarehouseDBIO {

  public boolean registerSection(String id, int width, int length, int height, int sectionCapacity) {

        String query = "INSERT section VALUES(?,?,?,?,?,?,?,?)";

    try (Connection connection = open();
        PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, inputStr("섹션ID"));
      pstmt.setString(2, id);
      pstmt.setInt(3, width);
      pstmt.setInt(4, length);
      pstmt.setInt(5, height);
      pstmt.setString(6, inputStr("섹션종류(냉장,냉동,실온)"));
      pstmt.setInt(7, sectionCapacity);
      pstmt.setString(8, inputStr("비고"));
      pstmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public ResultSet selectSection() {
    String query = "SELECT * FROM section";
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
    String query = "SELECT * FROM section WHERE " + column + " = ?";
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


  public boolean updateSection(HashMap<String, String> columns, String id) {
    StringBuilder query = new StringBuilder()
        .append("UPDATE section SET ");
    for (String column : columns.keySet()) {
      query.append(column).append(" = ?, ");
    }
    query.setLength(query.length() - 2);
    query.append(" where section_id = ?");

    try {
      open();
      readyPstmt(query.toString());
      getPstmt().setString(1, id);
      int index = 2;
      for (String column : columns.values()) {
        getPstmt().setString(index++, column);
      }
      getPstmt().executeUpdate();
      return true;
    } catch (SQLException e) {
      return false;
    } finally {
      close(getPstmt());
    }
  }

  public boolean deleteSection(String value) {
    String query = "DELETE FROM section WHERE section_id = ?";
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
