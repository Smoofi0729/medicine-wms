/*
package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WMS {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static Manager manager = Manager.getInstance();
  static int nsel;
  public static void main(String[] args) throws IOException {
    WMS Instance = new WMS();
    String buffer = null;
    while(true){
      System.out.println("===========[메뉴를 선택하세요.]===========");
      System.out.println("           1. WMS 시작");
      System.out.println("           2. WMS 종료");
      System.out.print(">>메뉴선택: ");
      nsel = System.in.read() - 48;
      System.in.skip(System.in.available());
      switch (nsel){
        case 1:
          while(true){
            System.out.println("===========[WMS]===========");
            System.out.println("        1. 입고");
            System.out.println("        2. 출고");
            System.out.println("        3. 재고현황");
            System.out.println("        4. 재무현황");
            System.out.println("        5. 이전으로");
            System.out.print(">>메뉴선택: ");
            nsel = System.in.read() - 48;
            System.in.skip(System.in.available());
            switch (nsel){
              case 1:
                Instance.serviceInbound();
                continue;
              case 2:
                continue;
              case 5:
                break;
            }
            break;
          }
          continue;
        case 2: break;
        default: continue;
      }
      break;
    }
  }
  public void serviceInbound() throws IOException {
    while(true){
      System.out.println("===========[1. 입고]===========");
      System.out.println("        1. 입고검수");
      System.out.println("        2. 입고승인");
      System.out.println("        3. 입고현황");
      System.out.println("        4. 이전으로");
      System.out.print(">>메뉴선택: ");
      nsel = System.in.read() - 48;
      System.in.skip(System.in.available());
      switch (nsel){
        case 1:
          manager.inspectInbound();
          continue;
        case 2:
          manager.approveInbound();
          continue;
        case 3:
          manager.showInboundInfo();
          continue;
        case 4:
          break;
      }
      break;
    }
  }

}
*/
