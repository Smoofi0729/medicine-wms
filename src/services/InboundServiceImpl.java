//package services;
//
//import DTO.InboundApproval;
//import DTO.InboundInspection;
//import DTO.InboundRequest;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class InboundServiceImpl extends InboundDBIO{
//  public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//  public static Scanner sc = new Scanner(System.in);
//
//  public void processInboundRequest(String memberId){
//    System.out.println("=======================[1. 입고요청]======================");
//    System.out.print(" 상품ID: ");
//    String productId = sc.nextLine();
//    System.out.print("상품수량: ");
//    int quantity = Integer.parseInt(sc.nextLine());
//    int insertRowNum = super.addInboundRequest(new InboundRequest(memberId, productId, quantity));
//    if (insertRowNum == 1) {
//      System.out.println(">>>>>> 상품 입고가 요청되었습니다 <<<<<<");
//    }
//  }
//
//  public void processInboundInspection(String managerId){ // 1. 입고검수 현황 출력 2. "Complete"되지 않은 검수 결과 갱신
//    ArrayList<InboundInspection> inboundInspectionsList = super.getInboundInspection();
//    int updated_row = 0;
//    System.out.println("=======================[2. 입고요청 검수]======================");
//    System.out.println(" 입고요청ID || 검수날짜 || 검수담당자 ID || 검수결과");
//    for(InboundInspection ii: inboundInspectionsList){
//      System.out.printf(" %s     %s     %s     %s\n", ii.getRequestId(), ii.getInspectionDate(), ii.getInspectorId(), ii.getInspectionStatus());
//    }
//    System.out.print(">> 입고요청ID(작업을 원치 않는 경우, '0' 입력): ");
//    String requestId = sc.nextLine();
//    System.out.print(">> 검수결과(Completed, Pending, InProgress): ");
//    String inspectionStatus = sc.nextLine();
//    if(!(requestId.equals("0"))){
//      updated_row = super.updateInboundInspection(requestId, managerId, inspectionStatus);
//    }
//    if (updated_row != 0) {
//      System.out.println(">>>>>>검수결과 " + updated_row+"건이 정상 반영되었습니다.<<<<<<");
//    }else{
//      System.err.println(">>>>>>검수결과 " + updated_row+"건이 반영되지 못했습니다.<<<<<<");
//    }
//
//  }
//
//  public void processInboundApproval(String managerId){ // 1. 입고검수 완료된 요청 현황 출력 2. 승인 결과 갱신
//    ArrayList<InboundApproval> inboundIApprovalList = super.getInboundApproval();
//    int updated_row = 0;
//    System.out.println("======================[3. 입고요청 승인]======================");
//    System.out.println(" 입고요청 ID || 승인날짜 || 승인담당자 ID || 승인결과");
//    for(InboundApproval ia: inboundIApprovalList){
//      System.out.printf(" %s     %s     %s     %s\n", ia.getRequestId(), ia.getApprovalDate(), ia.getApproverId(), ia.getApprovalStatus());
//    }
//    System.out.print(">> 입고요청ID(작업을 원치 않는 경우, '0' 입력): ");
//    String requestId = sc.nextLine();
//    System.out.print(">> 승인결과(Approved, Rejected): ");
//    String approvalStatus = sc.nextLine();
//    if(!(requestId.equals("0"))){
//      updated_row = super.updateInboundApproval(requestId, managerId, approvalStatus);
//    }
//    if (updated_row != 0) {
//      System.out.println(">>>>>>검수결과 " + updated_row+"건이 정상 반영되었습니다.<<<<<<");
//    }else{
//      System.err.println(">>>>>>검수결과 " + updated_row+"건이 반영되지 못했습니다.<<<<<<");
//    }
//
//  }
//
//  public void printInboundRequest() { // 입고요청 현황 출력 메서드_관리자 전용
//    ArrayList<InboundRequest> inboundRequests = super.getInboundRequestForManager();
//    System.out.println("======================[4. 입고요청 현황]======================");
//    System.out.println(" 입고요청 ID || 입고요청 날짜 || 요청회원 ID || 상품명ID || 상품수량 ");
//    for(InboundRequest ib: inboundRequests){
//      System.out.printf("      %s      %s     %s        %s       %d\n", ib.getRequestId(), ib.getRequestDate(), ib.getMemberId(), ib.getProductId(), ib.getQuantity());
//    }
//  }
//
//  public void printInboundRequest(String memberId) { // 입고요청 현황 출력 메서드_회원전용
//    ArrayList<InboundRequest> inboundRequests = super.getInboundRequestForMember(memberId);
//    System.out.println("======================[4. 입고요청 현황]======================");
//    System.out.println(" 입고요청 ID || 입고요청 날짜 || 상품명 || 상품수량 || 검수상태");
//    for(InboundRequest ib: inboundRequests){
//      System.out.printf("     %s      %s      %s      %s      %s\n", ib.getRequestId(), ib.getRequestDate(), ib.getProductId(), ib.getQuantity(), ib.getInspectionStatus());
//    }
//  }
//}
