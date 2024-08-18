package services;

import vo.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class InboundServiceImpl extends InboundDBIO{
  public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  public static Scanner sc = new Scanner(System.in);

  public void processInboundInspection(){ // 1. 입고검수 현황 출력 2. "Complete"되지 않은 검수 결과 갱신
    ArrayList<InboundInspection> inboundInspectionsList = super.getInboundInspection();
    int updated_row = 0;
    System.out.println("===========[1. 입고요청 검수]===========");
    System.out.println(" 입고요청 ID || 검수날짜 || 검수담당자 ID || 검수결과");
    for(InboundInspection ii: inboundInspectionsList){
      System.out.printf(" %s     %s     %s     %s\n", ii.getRequestId(), ii.getInspectionDate(), ii.getInspectorId(), ii.getInspectionStatus());
    }
    System.out.print(">> 입고요청ID(작업을 원치 않는 경우, '0' 입력): ");
    String requestId = sc.nextLine();
    System.out.print(">> 검수결과(Completed, Pending): ");
    String inspectionStatus = sc.nextLine();
    if(!(requestId.equals("0"))){
      updated_row = super.updateInboundInspection(requestId, inspectionStatus);
    }
    if (updated_row != 0) {
      System.out.println(">>>>>>검수결과 " + updated_row+"건이 정상 반영되었습니다.<<<<<<");
    }else{
      System.err.println(">>>>>>검수결과 " + updated_row+"건이 반영되지 못했습니다.<<<<<<");
    }

  }

  public void processInboundApproval(){ // 1. 입고검수 완료된 요청 현황 출력 2. 승인 결과 갱신
    ArrayList<InboundApproval> inboundIApprovalList = super.getInboundApproval();
    int updated_row = 0;
    System.out.println("===========[2. 입고요청 승인]===========");
    System.out.println(" 입고요청 ID || 승인날짜 || 승인담당자 ID || 승인결과");
    for(InboundApproval ia: inboundIApprovalList){
      System.out.printf(" %s     %s     %s     %s\n", ia.getRequestId(), ia.getApprovalDate(), ia.getApproverId(), ia.getApprovalStatus());
    }
    System.out.print(">> 입고요청ID(작업을 원치 않는 경우, '0' 입력): ");
    String requestId = sc.nextLine();
    System.out.print(">> 승인결과(Approved, Rejected): ");
    String approvalStatus = sc.nextLine();
    if(!(requestId.equals("0"))){
      updated_row = super.updateInboundApproval(requestId, approvalStatus);
    }
    if (updated_row != 0) {
      System.out.println(">>>>>>검수결과 " + updated_row+"건이 정상 반영되었습니다.<<<<<<");
    }else{
      System.err.println(">>>>>>검수결과 " + updated_row+"건이 반영되지 못했습니다.<<<<<<");
    }

  }

  public void showInboundRequest() { // 입고요청 현황 출력 메서드
    ArrayList<InboundRequest> inboundRequests = super.getInboundRequest();
    System.out.println("===========[3. 입고요청 현황]===========");
    System.out.println(" 입고요청 ID || 입고요청 날짜 || 요청회원 ID");
    for(InboundRequest ib: inboundRequests){
      System.out.printf(" %s      %s     %s\n", ib.getReqeustId(), ib.getRequestDate(), ib.getMemberId());
    }
  }
}
