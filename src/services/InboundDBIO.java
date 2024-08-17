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
    return null;
  }

  @Override
  public void updateInboundInspection(String requestId) {

  }

  @Override
  public ArrayList<InboundRequest> getInboundRequest() {
    ArrayList<InboundRequest> inboundREquestList = new ArrayList<>();
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
        inboundREquestList.add(inboundRequest);
      }
      rs.close();
      super.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }

    return inboundREquestList;
  }
}
