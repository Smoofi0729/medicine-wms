package dao.release;

import static enums.Messeges.DEVIDER;
import static enums.Messeges.printMessage;

import enums.ApprovalStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ReleaseInspectionDao extends ReleaseDBIO {

  // 검수초기데이터는 출고요청이 등록(insert)되면 trigger에 의해 자동등록됨 -> 실제 검수 후 데이터를 수정하는 방식
  // 삭제 역시 출고요청데이터 삭제(delete) 시에만 trigger에 의해 삭제연동되도록 설정함
  public ResultSet selectReleaseInspection() {
    String query = "SELECT * FROM release_inspection";
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
    String query = "SELECT * FROM release_inspection WHERE " + column + " = ?";
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

  public void checkInspectionResult(String status) {
    String query = "SELECT release_insptId FROM release_inspection where inspection_result = ?";

    try {
      open();
      readyPstmt(query);
      getPstmt().setString(1, status);
      setRs(getPstmt().executeQuery());
      System.out.println("처리상태가 '" +status+ "' 인 출고검수ID 목록");
      printMessage(DEVIDER);
      while (getRs().next()) {
          System.out.println(getRs().getString("release_insptId"));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean updateReleaseInspection(HashMap<String, String> columns, String id) {
    StringBuilder query = new StringBuilder()
        .append("UPDATE release_inspection SET ");
    for (String column : columns.keySet()) {
      query.append(column)
          .append(" = ?, ");
    }
    query.setLength(query.length() - 2);
    query.append(" where release_insptId = ?");

    try {
      open();
      readyPstmt(query.toString());
      int index = 1;
      for (String value : columns.values()) {

        getPstmt().setString(index++, value);
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
}
