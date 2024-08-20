package services;

import config.ObjectDBIO2;
import vo.*;
import interfaces.InboundIO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class InboundDBIO extends ObjectDBIO2 implements InboundIO {

  @Override
  public ArrayList<InboundApproval> getInboundApproval() {
    ArrayList<InboundApproval> inboundApprovalList = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String query = "SELECT * FROM InboundApproval";
    ResultSet rs = null;
    rs = super.execute(query, rs);

    try {
      while(rs.next()){
        String requestId = rs.getString("request_id");
        String approverId = rs.getString("approver_id");
        Date originDate = rs.getDate("approval_date");
        String approvalStatus = rs.getString("approval_status");
        String approvalDate = (originDate != null) ? sdf.format(originDate) : "   null   ";
        InboundApproval inboundApproval = new InboundApproval(requestId, approvalDate, approverId, approvalStatus);
        inboundApprovalList.add(inboundApproval);
      }
      rs.close();
      super.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }

    return inboundApprovalList;
  }

  @Override
  public int updateInboundApproval(String requestId, String managerID, String approvalStatus) {
    String query = "UPDATE InboundApproval " +
        "SET approval_status = '" + approvalStatus + "', " +
        "approver_id = '" + managerID + "' " +
        "WHERE request_id = '" + requestId + "'";
    return super.execute(query);
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
        String inspectorId = rs.getString("member_id");
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
  public int updateInboundInspection(String requestId, String managerID, String inspectionStatus) {
      //1. 입고요청ID를 입력->검수결과 입력("Completed", "InProgress", "Pending", null)
      //2. 검수결과 DB에 반영
      String query = "UPDATE InboundInspection " +
        "SET inspection_status = '" + inspectionStatus + "', " +
        "member_id = '" + managerID + "' " +
        "WHERE request_id = '" + requestId + "'";
      return super.execute(query);
  }

  @Override
  public int addInboundRequest(InboundRequest inboundRequest) {
    String memberId = inboundRequest.getMemberId();
    String productId = inboundRequest.getProductId();
    int quantity = inboundRequest.getQuantity();
    String query = String.format(
        "INSERT INTO InboundRequest (member_id, product_id, product_quantity) VALUES ('%s', '%s', %d)",
        memberId.replace("'", "''"), // SQL 인젝션을 방지하기 위한 간단한 처리
        productId.replace("'", "''"), // SQL 인젝션을 방지하기 위한 간단한 처리
        quantity
    );
    return super.execute(query);
  }

  @Override
  public ArrayList<InboundRequest> getInboundRequestForMember(String memberId) {
    ArrayList<InboundRequest> inboundRequestList = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String query = "SELECT a.request_id, request_date, product_id, product_quantity, inspection_status FROM "+
        "InboundRequest a, InboundInspection b WHERE a.member_id = '"+memberId+"' and a.request_id = b.request_id";
    ResultSet rs = null;
    rs = super.execute(query, rs);
    try {
      while(rs.next()){
        String requestId = rs.getString("request_id");
        Date originDate = rs.getDate("request_date");
        String requestDate = sdf.format(originDate);
        String productId = rs.getString("product_id");
        int quantity = rs.getInt("product_quantity");
        String originStatus = rs.getString("inspection_status");
        String inspectionStatus = (originStatus != null) ? originStatus : "   null   ";
        //String approvalStatus = rs.getString("approval_status");
        InboundRequest inboundRequest = new InboundRequest(requestId, requestDate, memberId, productId,  quantity, inspectionStatus);
        inboundRequestList.add(inboundRequest);
      }
      rs.close();
      super.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return inboundRequestList;
  }

  @Override
  public ArrayList<InboundRequest> getInboundRequestForManager() {
    ArrayList<InboundRequest> inboundRequestList = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String query = "SELECT * FROM InboundRequest";
    ResultSet rs = null;
    rs = super.execute(query, rs);

    try {
      while(rs.next()){
        String requestId = rs.getString("request_id");
        Date originDate = rs.getDate("request_date");
        String requestDate = sdf.format(originDate);
        String memberId = rs.getString("member_id");
        String productId = rs.getString("product_id");
        //String productName = rs.getString("product_name");
        int quantity = rs.getInt("product_quantity");
        //String inspectionStatus = rs.getString("inspection_status");
        //String approvalStatus = rs.getString("approval_status");
        InboundRequest inboundRequest = new InboundRequest(requestId, requestDate, memberId, productId, quantity);
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
