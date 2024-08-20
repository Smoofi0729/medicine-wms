//package controller;
//
//import java.util.Scanner;
//import services.InboundServiceImpl;
//
//public class InboundService {
//  public static Scanner sc = new Scanner(System.in);
//  public static int nsel;
//  public static int auto_increment = 0;
///*  public static void main(String[] args) {
//    InboundService Instance = new InboundService();
//    System.out.print("회원구분 입력(관리자:1, 회원:2): ");
//    int nsel = sc.nextInt();
//    if(nsel == 1)
//      Instance.serviceInboundForManager("G001");
//    else
//      Instance.serviceInboundForMember("M003");
//  }*/
//
//  public void serviceInboundForMember(String memberId){ //회원의 입고정보에 대해서만
//    InboundServiceImpl InboundService = new InboundServiceImpl();
//    while(true){
//      System.out.println("======================[1. 입고]======================");
//      System.out.println("        1. 입고요청");
//      System.out.println("        2. 입고요청 현황");
//      System.out.println("        3. 이전으로");
//      System.out.print(">>메뉴선택: ");
//      nsel = sc.nextInt();
//      switch (nsel){
//        case 1:
//          InboundService.processInboundRequest(memberId);
//          continue;
//        case 2:
//          InboundService.printInboundRequest(memberId);
//          continue;
//        case 3:
//          break;
//      }
//      break;
//    }
//  }
//
//  public void serviceInboundForManager(String managerId){ //전체입고 정보에 대해서
//    InboundServiceImpl InboundService = new InboundServiceImpl();
//    while(true){
//      System.out.println("======================[1. 입고]======================");
//      System.out.println("        1. 입고요청 검수");
//      System.out.println("        2. 입고요청 승인");
//      System.out.println("        3. 입고요청 현황");
//      System.out.println("        4. 이전으로");
//      System.out.print(">>메뉴선택: ");
//      nsel = sc.nextInt();
//      switch (nsel){
//        case 1:
//          InboundService.processInboundInspection(managerId);
//          continue;
//        case 2:
//          InboundService.processInboundApproval(managerId);
//          continue;
//        case 3:
//          InboundService.printInboundRequest();
//          continue;
//        case 4:
//          break;
//      }
//      break;
//    }
//  }
//
//}
