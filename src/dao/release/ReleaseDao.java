package dao.release;

import config.enums.ApprovalStatus;
import java.sql.CallableStatement;
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

  public void callMyReleaseProc(String id) {
    String query = "{CALL GetMemberReleaseStatus(?)}";
    try (CallableStatement cstmt = getConnection().prepareCall(query)) {
      cstmt.setString(1, id);
      try (ResultSet rs = cstmt.executeQuery()) {
        while (rs.next()) {
          System.out.println("출고요청ID: " + rs.getString("release_reqId"));
          System.out.println("출고물품번호: " + rs.getString("product_lotno"));
          System.out.println("주문수량: " + rs.getInt("order_quantity"));
          System.out.println("출고날짜: " + rs.getString("releases_date"));
          System.out.println("배송상태: " + rs.getString("delivery_status"));
          System.out.println("배송출발날짜: " + rs.getDate("departure_date"));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
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
