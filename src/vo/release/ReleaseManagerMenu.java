package vo.release;

import config.UtilMethod;
import interfaces.release.ReleaseInspectionService;
import interfaces.release.ReleaseRequestService;
import interfaces.release.ReleaseService;

public class ReleaseManagerMenu {

  private ReleaseService releaseService;
  private ReleaseRequestService releaseRequestService;
  private ReleaseInspectionService releaseInspectionService;


  public void releaseMenu() {
    while (true) {
      System.out.println("=====================================================================");
      System.out.println("출고 메뉴");
      System.out.println("=====================================================================");

      System.out.println("1. 출고요청관리 | 2. 출고검수관리 | 3. 출고관리 | 4. 배차관리 | 5. 운송장관리");
      switch (UtilMethod.inputInt("메뉴선택")) {
        case 1 -> releaseRequestService.releaseRequestMenuForManager();
        case 2 -> releaseInspectionService.releaseInspectionMenu();
        case 3 -> releaseService.releaseMenu();

        default -> System.out.println("입력이 잘못되었습니다.");
      }
    }
  }
}