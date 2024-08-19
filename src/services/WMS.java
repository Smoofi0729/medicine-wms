
package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WMS {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static InboundServiceImpl InboundService = new InboundServiceImpl();
  static int nsel;
  public void serviceInbound() throws IOException {
    while(true){
      System.out.println("===========[1. 입고]===========");
      System.out.println("        1. 입고요청");
      System.out.println("        2. 입고요청 검수");
      System.out.println("        3. 입고요청 승인");
      System.out.println("        4. 입고요청 현황");
      System.out.println("        5. 이전으로");
      System.out.print(">>메뉴선택: ");
      nsel = System.in.read() - 48;
      System.in.skip(System.in.available());
      switch (nsel){
        case 1:
          InboundService.processInboundRequest();
          continue;
        case 2:
          InboundService.processInboundInspection();
          continue;
        case 3:
          InboundService.processInboundApproval();
          continue;
        case 4:
          InboundService.showInboundStatus();
          continue;
        case 5:
          break;
      }
      break;
    }
  }

}
