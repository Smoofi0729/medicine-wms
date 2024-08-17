package services;

import VO.InboundData;
import VO.InspectionData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Manager extends InboundDBIO{
  private static final Manager manager = new Manager();

  private Manager(){}

  public static Manager getInstance(){
    return  manager;
  }

  @Override
  public boolean approveInbound() {
    return super.approveInbound();
  }


  @Override
  public void saveInboundData(InboundData ibdata) {
    super.saveInboundData(ibdata);
  }

  public void showInboundInfo() {
    ArrayList<InboundData> ibinfo = super.searchInbound();
    System.out.println("===========[3. 입고현황]===========");
    System.out.println(" 입고ID || 입고날짜 || 회원ID");
    for(InboundData ib: ibinfo){
      System.out.printf(" %s  %s  %s\n", ib.getRequestId(), ib.getRequest_date(), ib.getMemberId());
    }
  }

  @Override
  public void inspectInbound() throws IOException {
    ArrayList<InspectionData> inspectionData = super.getInspection();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("===========[1. 입고검수]===========");
    System.out.println(" 요청ID || 검수날짜 || 관리자ID || 검수현황");
    for(InspectionData is:inspectionData){
      System.out.printf(" %s  %s  %s  %s\n", is.getRequest_id(), is.getInspection_date(), is.getInspector_id(), is.getInspection_result());
    }
    System.out.print(">>요청ID(이전작업 '0' 입력):");
    String requestId = br.readLine();
    if(!(requestId.equals("0"))){
      System.out.print(">>검수현황(Pending, Approved, Rejected):");
      String inspectionResult = br.readLine();
      super.updateInspection(requestId, inspectionResult);
    }
  }
}
