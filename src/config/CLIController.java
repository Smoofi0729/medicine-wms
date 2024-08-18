package config;

import config.ConnectionFactory;
import services.AdminFunctions;
import services.memberServices;
import vo.LOGO;
import vo.UserMessege;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CLIController {
    private Connection connection; // Changed to non-static

    public void BasicMenu() throws Exception { // Changed to non-static
        boolean validinput = false;

        LOGO.logo(); // 로고
        UserMessege.START_WELCOME_MESSEGE.println(); // 인사말

        while (!validinput) {
            UserMessege.START_MAIN_MENU.println();
            UserMessege.MENU_CHOICE.print();

            switch (SystemIn.SystemInInt()) {

                // 로그인
                case 1:
                    System.out.println("로그인");
                    memberServices.login();
                    validinput = true;
                    break;

                // 회원가입
                case 2:
                    System.out.println("회원가입");
                    memberServices.signInMember();
                    validinput = true;
                    break;

                // ID 찾기
                case 3:
                    System.out.println("ID 찾기");
                    validinput = true;
                    memberServices.findId();
                    break;

                // 비밀번호 찾기
                case 4:
                    System.out.println("비밀번호 찾기");
                    validinput = true;
                    memberServices.resetPassword();
                    break;

                default:
                    System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요.");
            }
        }
    }

    public void MemberMainMenu(String memberId) throws IOException { // Changed to non-static
        boolean exit = false;
        while (!exit) {
            System.out.println(memberId + " 님 환영합니다.");
            System.out.println("메인 메뉴 : 1.입고 | 2.출고 | 3.재고 | 4.재무 | 5.고객센터 | 6.회원정보 | 7.로그아웃");
            int select = SystemIn.SystemInInt();
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
                    memberServices.viewMemberInfo(memberId);
                    break;
                case 7:
                    System.out.println("로그아웃");
                    exit = true;
                    System.out.println("회원 " + memberId + " 님이 로그아웃되었습니다.");
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    public void DeliveryMainMenu(String memberId) throws IOException { // Changed to non-static
        boolean exit = false;
        while (!exit) {
            System.out.println(memberId + " 님 환영합니다.");
            System.out.println("메인 메뉴 : 1.배송 | 2.고객센터 | 3.회원정보 | 4.로그아웃");
            int select = SystemIn.SystemInInt();
            switch (select) {
                case 1:
                    System.out.println("배송");
                    break;
                case 2:
                    System.out.println("고객센터");
                    break;
                case 3:
                    System.out.println("회원정보");
                    memberServices.viewMemberInfo(memberId);
                    break;
                case 4:
                    System.out.println("로그아웃");
                    exit = true;
                    System.out.println("회원 " + memberId + " 님이 로그아웃되었습니다.");
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    public void adminMainMenu(String memberId) throws IOException, SQLException { // Changed to non-static
        boolean exit = false;
        while (!exit) {
            System.out.println(memberId + " 님 환영합니다.");
            System.out.println("메인 메뉴 : 1.입고 | 2.출고 | 3.재고 | 4.재무 | 5.고객센터 | 6.회원정보 관리 | 7.로그아웃");
            int select = SystemIn.SystemInInt();
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
                    manageMemberInfo(memberId);
                    break;
                case 7:
                    System.out.println("회원 " + memberId + " 님이 로그아웃되었습니다.");
                    exit = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    private void manageMemberInfo(String memberId) throws IOException, SQLException { // Changed to non-static and private
        boolean exit = false;
        while (!exit) {
            System.out.println("1. 개인 정보 관리 | 2. 회원 정보 관리 | 3. 뒤로가기");
            int select = SystemIn.SystemInInt();
            switch (select) {
                case 1:
                    memberServices.viewMemberInfo(memberId);
                    break;
                case 2:
                    manageAllMembers();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    private void manageAllMembers() throws SQLException, IOException { // Changed to non-static and private
        boolean exit = false;
        while (!exit) {
            System.out.println("회원 정보 관리 : 1.모든 사용자 조회 | 2.회원 타입별 조회 | 3.회원 ID로 조회 | 4.권한 승인 요청 조회 | 5.권한 승인 |  6.삭제 | 7.뒤로가기");
            int select = SystemIn.SystemInInt();
            AdminFunctions adminFunctions = new AdminFunctions();
            switch (select) {
                case 1:
                    // 모든 사용자 조회
                    adminFunctions.searchAllMembers();
                    break;
                case 2:
                    // 회원 타입별 조회
                    System.out.println("조회할 회원 타입을 입력하세요 (일반회원, 배송기사, 관리자):");
                    String memberType = SystemIn.SystemInString();
                    adminFunctions.searchMembersByType(memberType);
                    break;
                case 3:
                    // 회원 ID로 조회
                    System.out.println("조회할 회원 ID를 입력하세요:");
                    String memberId = SystemIn.SystemInString();
                    adminFunctions.searchMemberById(memberId);
                    break;
                case 4:
                    // 권한 승인 요청 조회
                    adminFunctions.searchMembersWithPendingApproval();
                    break;
                case 5:
                    // 권한 승인
                    System.out.println("승인할 회원 ID를 입력하세요:");
                    memberId = SystemIn.SystemInString();
                    adminFunctions.approveMember(memberId);
                    break;

                case 6:
                    // 회원 삭제 승인
                    System.out.println("비활성화할 회원 ID를 입력하세요:");
                    memberId = SystemIn.SystemInString();
                    adminFunctions.finalizeMemberDeletion(memberId);
                    break;
                case 7:
                    // 뒤로가기
                    exit = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    public void showMenu(String memberId) throws SQLException, IOException { // Changed to non-static
        try {
            connection = ConnectionFactory.getInstance().open();
            String sql = "SELECT member_type FROM member WHERE member_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, memberId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String memberType = rs.getString("member_type");
                        switch (memberType) {
                            case "일반회원":
                                MemberMainMenu(memberId);
                                break;
                            case "배송기사":
                                DeliveryMainMenu(memberId);
                                break;
                            case "관리자":
                                adminMainMenu(memberId);
                                break;
                            default:
                                System.out.println("회원정보를 찾을 수 없습니다.");
                                break;
                        }
                    } else {
                        System.out.println("회원정보를 찾을 수 없습니다.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
