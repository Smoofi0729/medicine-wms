package services;

import java.io.IOException;


public class UserMenu {


    public void userMainMenu() throws IOException {
        while (true) {
            int select = CommonMenu.userChoice();  //
            switch (select) {
                case 1:
                    System.out.println("입고");
                    break;
                case 2:
                    System.out.println("출고");
                    break;
                case 3:
                    System.out.println("재고");
                    break;
                case 4:
                    System.out.println("재무");
                    break;
                case 5:
                    System.out.println("고객센터");
                    break;
                case 6:
                    System.out.println("회원정보");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
            }
        }
    }

    public void userInboundMenu() throws IOException {
        while (true) {
            int select = CommonMenu.userChoice();  //
            switch (select) {
                case 1:
                    System.out.println("입고요청");
                    break;
                case 2:
                    System.out.println("입고요청 취소");
                    break;
                case 3:
                    System.out.println("입고요청 수정");
                    break;
                case 4:
                    System.out.println("입고요청 조회");
                    break;
                case 5:
                    System.out.println("입고 현황확인");
                    break;
                case 6:
                    System.out.println("기간별 입고현황");
                    break;
                case 7:
                    System.out.println("월별 입고현황");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
            }
        }
    }

    public void userOutboundMenu() throws IOException {
        while (true) {
            int select = CommonMenu.userChoice();  //
            switch (select) {
                case 1:
                    System.out.println("출고요청");
                    break;
                case 2:
                    System.out.println("출고요청 취소");
                    break;
                case 3:
                    System.out.println("출고요청 수정");
                    break;
                case 4:
                    System.out.println("출고요청 조회");
                    break;
                case 5:
                    System.out.println("출고리스트 조회");
                    break;
                case 6:
                    System.out.println("출고 상품 조회");
                    break;
                case 7:
                    System.out.println("운송장 조회");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
            }
        }
    }

    public void userStock() throws IOException {
        while (true) {
            int select = CommonMenu.userChoice();  //
            switch (select) {
                case 1:
                    System.out.println("재고 조회");
                    break;
                case 2:
                    System.out.println("제형 별 조회");
                    break;
                case 3:
                    System.out.println("보관법 별 조회");
                    break;
                case 4:
                    System.out.println("품목 현황 리스트");
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
            }
        }
    }

    public void userFinance() throws IOException {
        while (true) {
            int select = CommonMenu.userChoice();  //
            switch (select) {
                case 1:
                    System.out.println("요금 명세서 확인");
                    break;
                case 2:
                    System.out.println("납부 금액 확인");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
            }
        }
    }

    public void userServiceCenter() throws IOException {
        while (true) {
            int select = CommonMenu.userChoice();  //
            switch (select) {
                case 1:
                    System.out.println("공지사항 조회");
                    break;
                case 2:
                    System.out.println("문의 게시판 조회");
                    break;
                case 3:
                    System.out.println("문의 글 작성");
                    break;
                case 4:
                    System.out.println("문의 글 수정");
                    break;
                case 5:
                    System.out.println("문의글 삭제");
                    break;
                case 6:
                    System.out.println("1:1 문의글 생성");
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