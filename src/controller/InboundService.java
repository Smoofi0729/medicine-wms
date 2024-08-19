/*
package controller;

import services.InboundServiceImpl;

import java.util.Scanner;

public class InboundService {
  public static Scanner sc = new Scanner(System.in);
  public static int nsel;

  public static void main(String[] args) {
    InboundService Instance = new InboundService();
    System.out.println("회원구분 입력(관리자:1, 회원:2): ");
    int nsel = sc.nextInt();
    if(nsel == 1)
      Instance.serviceInboundForManager();
    else
      Instance.serviceInboundForMember("M001");
  }

  public void serviceInboundForMember(String memberId){ //회원의 입고정보에 대해서만
    InboundServiceImpl InboundService = new InboundServiceImpl();
    while(true){
      System.out.println("===========[1. 입고]===========");
      System.out.println("        1. 입고요청");
      System.out.println("        2. 입고요청 현황");
      System.out.println("        3. 이전으로");
      System.out.print(">>메뉴선택: ");
      nsel = sc.nextInt();
      switch (nsel){
        case 1:
          InboundService.processInboundRequest(memberId);
        case 2:
          InboundService.printInboundRequest(memberId);
          continue;
        case 3:
          break;
      }
      break;
    }
  }

  public void serviceInboundForManager(){ //전체입고 정보에 대해서
    InboundServiceImpl InboundService = new InboundServiceImpl();
    while(true){
      System.out.println("===========[1. 입고]===========");
      System.out.println("        1. 입고요청 검수");
      System.out.println("        2. 입고요청 승인");
      System.out.println("        3. 입고요청 현황");
      System.out.println("        4. 이전으로");
      System.out.print(">>메뉴선택: ");
      nsel = sc.nextInt();
      switch (nsel){
        case 1:
          InboundService.processInboundInspection();
          continue;
        case 2:
          InboundService.processInboundApproval();
          continue;
        case 3:
          InboundService.printInboundRequest();
          continue;
        case 4:
          break;
      }
      break;
    }
  }

}
*/
