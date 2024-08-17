package services;

import DTO.InboundRequest;
import java.util.ArrayList;

public class InboundServiceImpl extends InboundDBIO{

  public void showInboundRequest() {
    ArrayList<InboundRequest> inboundRequests = super.getInboundRequest();
    System.out.println("===========[3. 입고요청 현황]===========");
    System.out.println(" 입고요청 ID || 입고요청 날짜 || 요청회원 ID");
    for(InboundRequest ib: inboundRequests){
      System.out.printf(" %s      %s     %s\n", ib.getReqeustId(), ib.getRequestDate(), ib.getMemberId());
    }
  }
}
