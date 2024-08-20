package services.release;

import static config.UtilMethod.inputInt;
import static enums.Messeges.DEVIDER;
import static enums.Messeges.*;
import static enums.Messeges.printMessage;

import interfaces.release.ReleaseInspectionService;
import interfaces.release.ReleaseRequestService;
import interfaces.release.ReleaseService;
import java.io.IOException;
import java.sql.SQLException;

public class ReleaseMainMenu {

  private ReleaseService releaseService;
  private ReleaseRequestService releaseRequestService;
  private ReleaseInspectionService releaseInspectionService;

  public ReleaseMainMenu() {
    this.releaseService = new ReleaseServiceImpl();
    this.releaseRequestService = new ReleaseRequestServiceImpl();
    this.releaseInspectionService = new ReleaseInspectionServiceImpl();
  }

  public void showReleaseManageMenu(String memberId) throws SQLException, IOException {
    while (true) {
      printMessage(DEVIDER);
      printMessage(RL_MAINMENU);
      printMessage(DEVIDER);
      System.out.println("1. 출고요청관리 | 2. 출고검수관리 | 3. 출고, 배차 및 운송장 관리 | 4. 관리자메뉴로");
      switch (inputInt("메뉴선택")) {
        case 1 -> releaseRequestService.releaseRequestMenuForManager();
        case 2 -> releaseInspectionService.releaseInspectionMenu();
        case 3 -> releaseService.releaseMenuForManager(memberId);
        case 4 -> {return;}
        default -> printMessage(WRONG_INPUT);
      }
    }
  }
}
