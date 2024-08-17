package services;

import Connection.ObjectDBIO;
import DTO.InboundApproval;
import DTO.InboundInspection;
import DTO.InboundRequest;
import Interface.InboundIO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class InboundDBIO extends ObjectDBIO implements InboundIO {

  @Override
  public ArrayList<InboundApproval> getInboundApproval() {
    return null;
  }

  @Override
  public void updateInboundApproval(String requestId) {

  }

  @Override
  public ArrayList<InboundInspection> getInboundInspection() {
    ArrayList<InboundInspection> inboundInspectionList = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String query = "SELECT * FROM InboundInspection";
    ResultSet rs = null;
    rs = super.execute(query, rs);

    try {
      while(rs.next()){
        String requestId = rs.getString("request_id");
        String inspectorId = rs.getString("inspector_id");
        Date originDate = rs.getDate("inspection_date");
        String inspectionStatus = rs.getString("inspection_status");
        String inspectionDate = (originDate != null) ? sdf.format(originDate) : "   null   ";
        InboundInspection inboundInspection = new InboundInspection(requestId, inspectionDate, inspectorId, inspectionStatus);
        inboundInspectionList.add(inboundInspection);
      }
      rs.close();
      super.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }

    return inboundInspectionList;
  }

  @Override
  public int updateInboundInspection(String requestId, String inspectionStatus) {
      //1. 입고요청ID를 입력->검수결과 입력("Completed", "InProgress", "Pending", null)
      //2. 검수결과 DB에 반영
      String query = "UPDATE InboundInspection SET inspection_status = '"+inspectionStatus+"' WHERE request_id = '"+requestId+"'";
      return super.execute(query);
  }

  @Override
  public ArrayList<InboundRequest> getInboundRequest() {
    ArrayList<InboundRequest> inboundRequestList = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String query = "SELECT * FROM InboundRequest";
    ResultSet rs = null;
    rs = super.execute(query, rs);

    try {
      while(rs.next()){
        String requestId = rs.getString("request_id");
        String memberId = rs.getString("member_id");
        Date originDate = rs.getDate("request_date");
        String requestDate = sdf.format(originDate);
        InboundRequest inboundRequest = new InboundRequest(requestId, requestDate,memberId);
        inboundRequestList.add(inboundRequest);
      }
      rs.close();
      super.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }

    return inboundRequestList;
  }
}
