package dao.release;

import static config.UtilMethod.autoCreateId;
import static config.UtilMethod.inputInt;
import static config.UtilMethod.inputStr;

import config.enums.ApprovalStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ReleaseRequestDao extends ReleaseDBIO {

  public boolean registerReleaseRequest() {

    String query = "INSERT release_request VALUES(?,?,?,?,?,?,?,?,?,?,?)";

    try (Connection connection = open();
        PreparedStatement pstmt = connection.prepareStatement(query)) {
      System.out.println("*표시는 필수입력사항입니다.");
      pstmt.setString(1, autoCreateId("출고요청", LocalDateTime.now()));
      pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
      pstmt.setString(3, inputStr("주문회원ID(*)"));
      pstmt.setString(4, inputStr("출고물품ID(*)"));
      pstmt.setInt(5, inputInt("주문수량(*)"));
      pstmt.setString(6, inputStr("수취인이름(*)"));
      pstmt.setString(7, inputStr("수취인주소(*)"));
      pstmt.setString(8, inputStr("수취인연락처(*)"));
      pstmt.setString(9, inputStr("주문요청사항"));
      pstmt.setString(10, ApprovalStatus.ON_PROCESS.getDescription());
      pstmt.setString(11, inputStr("비고"));
      pstmt.executeUpdate();
      System.out.println("등록성공");
      return true;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public ResultSet selectReleaseRequest() {
    String query = "SELECT * FROM release_request";
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
    String query = "SELECT * FROM release_request WHERE " + column + " = ?";
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

  public ResultSet selectFilterByLike(String column, String value) {
    String query = "SELECT * FROM release_request WHERE " + column + " LIKE ?";
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

  public void selectRequestIdByStatus(ApprovalStatus status) {
    String query = "SELECT * FROM release_request where release_req_status = ?";
    try {
      open();
      readyPstmt(query);
      getPstmt().setString(1, status.getDescription());
      setRs(getPstmt().executeQuery());
      while (true) {
        if (getRs().next()) {
          System.out.print("처리상태가 " +status.getDescription()+ "인 출고요청ID 목록\n" + getRs().getString("release_reqId") + "\n");
        } else {
          System.out.println("데이터가 존재하지 않습니다.");
          break;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(getRs());
      close(getPstmt());
      close(getConnection());
    }
  }

  public String checkRequestStatus(String id) {
    String query = "SELECT release_req_status FROM release_request where release_reqId = ?";
    try {
      open();
      readyPstmt(query);
      getPstmt().setString(1, id);
      setRs(getPstmt().executeQuery());
      if (getRs().next()) {
        return getRs().getString("release_req_status");
      } else {
        System.out.println("데이터가 존재하지 않습니다.");
        return null;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(getRs());
      close(getPstmt());
    }
  }

  public boolean updateReleaseRequest(HashMap<String, String> columns, String id) {
    StringBuilder query = new StringBuilder()
        .append("UPDATE release_request SET ");
    for (String column : columns.keySet()) {
      query.append(column).append(" = ?, ");
    }
    query.setLength(query.length() - 2);
    query.append(" where release_reqId = ?");

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

  public boolean deleteReleaseRequest(String value) {
    String query = "DELETE FROM release_request WHERE release_reqId = ?";
    try {
      open();
      readyPstmt(query);
      getPstmt().setString(1, value);
      getPstmt().executeUpdate();
      return true;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally{
      close(getPstmt());
      close(getConnection());
    }
  }
}
