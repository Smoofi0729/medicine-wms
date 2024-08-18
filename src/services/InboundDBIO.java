/*
package services;

import VO.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class InboundDBIO extends ObjectDBIO implements InboundIO {

  @Override
  public boolean approveInbound() {
    return false;
  }


  @Override
  public ArrayList<InspectionData> getInspection() {
    ArrayList<InspectionData> inspectionList = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String query = "select * from Inspection";
    ResultSet rs = null;
    rs = super.execute(query, rs);
    try {
      while(rs.next()){
        String requestId = rs.getString("request_id");
        String inspectorId = rs.getString("inspector_id");
        Date originDate = rs.getDate("inspection_date");
        String inspectionDate = sdf.format(originDate);
        String inspectionResult = rs.getString("inspection_result");
        InspectionData inspectionData = new InspectionData(requestId, inspectionDate, inspectorId, inspectionResult);
        inspectionList.add(inspectionData);
      }
      rs.close();
      super.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }

    return inspectionList;
  }

  @Override
  public int updateInspection(String requestId, String inspectionResult) {
    String query = "UPDATE Inspection SET inspection_result = '"+inspectionResult+ "' WHERE request_id = '"+ requestId+"'";
    System.out.println(query);
    return super.execute(query);
  }

  @Override
  public void saveInboundData(InboundData ibdata) {

  }

  @Override
  public ArrayList<InboundData> searchInbound() {
    ArrayList<InboundData> inboundList = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String query = "select * from InboundRequest";
    ResultSet rs = null;
    rs = super.execute(query, rs);
    try {
      while(rs.next()){
        String requestId = rs.getString("request_id");
        String memberId = rs.getString("request_id");
        Date originDate = rs.getDate("request_date");
        String requestDate = sdf.format(originDate);
        InboundData inboundData = new InboundData(requestId, requestDate,memberId);
        inboundList.add(inboundData);
      }
      rs.close();
      super.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }

    return inboundList;
  }
}
*/
