package services;

import java.io.IOException;

public class DeliveryManMenu {


    public void DeliveryManMainMenu() throws IOException {
        while (true) {
            int select = CommonMenu.userChoice();  //
            switch (select) {
                case 1:
                    System.out.println("배송");
                    break;
                case 2:
                    System.out.println("고객센터");
                    break;
                case 3:
                    System.out.println("회원정보");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
            }
        }
    }

    public void DeliveryMenu() throws IOException {
        while (true) {
            int select = CommonMenu.userChoice();  //
            switch (select) {
                case 1:
                    System.out.println("운송장 조회");
                    break;
                case 2:
                    System.out.println("운행상태 변경");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
            }
        }
    }

    public void DeliveryServiceCenter() throws IOException {
        while (true) {
            int select = CommonMenu.userChoice();  //
            switch (select) {
                case 1:
                    System.out.println("공지사항 조회");
                    break;
                case 2:
                    System.out.println("문의 게시판 조회");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
            }
        }
    }

    public void UserInformation() throws IOException {
        while (true) {
            int select = CommonMenu.userChoice();  //
            switch (select) {
                case 1:
                    System.out.println("회원정보 조회");
                    break;
                case 2:
                    System.out.println("회원정보 수정");
                    break;
                case 3:
                    System.out.println("회원 탈퇴");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
            }
        }
    }


}
