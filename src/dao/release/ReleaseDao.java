package dao.release;

import enums.ApprovalStatus;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ReleaseDao extends ReleaseDBIO {
  // 출고는 출고검수상태가 "APPROVED"로 UPDATE 되면 자동등록되는 트리거사용

  public ResultSet selectData(String table) {
    String query = "SELECT * FROM " + table;
    try {
      open();
      readyPstmt(query);
      setRs(getPstmt().executeQuery());
      return getRs();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public ResultSet selectFilterBy(String table, String column, String value) {
    String query = "SELECT * FROM " + table + " WHERE " + column + " = ?";
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

  public void readDispatchByStatus(ApprovalStatus status) {
    String query = "SELECT dispatch_id FROM dispatch where dispatch_status = ?";

    try {
      open();
      readyPstmt(query);
      getPstmt().setString(1, status.getDescription());
      setRs(getPstmt().executeQuery());
      while (true) {
        if (getRs().next()) {
          System.out.print("등록대기중인 배차ID 목록\n " + getRs().getString("release_insptId") + "\n");
        } else {
          System.out.println("데이터가 존재하지 않습니다.");
          break;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void callMyRelease(String memberId) {
    String procedure = "{CALL GetMemberReleaseStatus()}";
    try (Connection connection = open();
        CallableStatement cstmt = connection.prepareCall(procedure)) {
      try (ResultSet rs = cstmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("예정된 출고가 없습니다.");
          return;
        }
        while (rs.next()) {
          System.out.print("출고요청ID: " + rs.getString("release_reqId") + "\t");
          System.out.print("출고물품번호: " + rs.getString("product_lotno") + "\t");
          System.out.print("주문수량: " + rs.getInt("order_quantity") + "\t");

          System.out.print("출고날짜: " + rs.getDate("release_date") + "\t");
          System.out.print("배송상태: " + rs.getString("delivery_status") + "\t");
          System.out.println("배송출발날짜: " + rs.getDate("departure_date") + "\t");
        }
      }
    } catch (SQLException e) {
      if (e.getMessage().contains("Column 'release_date' not found")) {
        System.out.println("(출고대기중)");
      } else if (e.getMessage().contains("Column 'delivery_status' not found")) {
        System.out.println("(배차대기중)");
      } else if (e.getMessage().contains("Column 'departure_date' not found")){
        System.out.println("(배송준비중)");
      }
    }
  }

  public boolean updateData(String table, HashMap<String, String> columns, String id) {
    StringBuilder query = new StringBuilder()
        .append("UPDATE " + table + " SET ");
    for (String column : columns.keySet()) {
      query.append(column).append(" = ?, ");
    }
    query.setLength(query.length() - 2);
    query.append(" where release_id = ?");

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

  public boolean deleteRelease(String value) {
    String query = "DELETE FROM releases WHERE release_id = ?";
    try {
      open();
      readyPstmt(query);
      getPstmt().setString(1, value);
      getPstmt().executeUpdate();
      return true;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(getPstmt());
      close(getConnection());
    }
  }
}
