package config;

import config.ConnectionFactory;
import services.InboundServiceImpl;
import services.WMS;
import services.memberServices;
import vo.LOGO;
import vo.UserMessege;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CLIController {
    private static Connection connection;
    public static void BasicMenu() throws Exception {
        boolean validinput = false;

        LOGO.logo(); //로고
        UserMessege.START_WELCOME_MESSEGE.println(); //인사말

        while (!validinput) {
            UserMessege.START_MAIN_MENU.println();
            UserMessege.MENU_CHOICE.print();

            switch (SystemIn.SystemInInt()) {

                //로그인
                case 1:
                    System.out.println("로그인");
                    memberServices.login();
                    validinput = true;
                    break;

                //회원가입
                case 2:
                    System.out.println("회원가입");
                    memberServices.signInMember();
                    validinput = true;
                    break;

                //ID 찾기
                case 3:
                    System.out.println("ID 찾기");
                    validinput = true;
                    memberServices.findId();
                    break;

                //비밀번호 찾기
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

    public static void MemberMainMenu(String memberId) throws IOException {
        WMS inboundservice = new WMS();
        System.out.println(memberId + " 님 환영합니다.");
        System.out.println("메인 메뉴 : 1.입고 | 2.출고 | 3.재고 | 4.재무 | 5.고객센터 | 6.회원정보");
        int select = SystemIn.SystemInInt();
        boolean validinput = false;
        while (!validinput) {
            switch (select) {
                case 1:
                    System.out.println("입고");
                    inboundservice.serviceInbound();
                    validinput = true;
                    break;
                case 2:
                    System.out.println("출고");
                    validinput = true;
                    break;
                case 3:
                    System.out.println("재고");
                    validinput = true;
                    break;
                case 4:
                    System.out.println("재무");
                    validinput = true;
                    break;
                case 5:
                    System.out.println("고객센터");
                    validinput = true;
                    break;
                case 6:
                    System.out.println("회원정보");
                    memberServices.viewMemberInfo(memberId);
                    validinput = true;
                    break;
                case 7:
                    System.out.println("로그아웃");
                    validinput = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    public static void DeliveryMainMenu(String memberId) throws IOException {
        System.out.println(memberId + " 님 환영합니다.");
        System.out.println("메인 메뉴 : 1.배송 | 2.고객센터 | 3.회원정보 | 4.로그아웃");
        int select = SystemIn.SystemInInt();
        boolean validinput = false;
        while (!validinput) {
            switch (select) {
                case 1:
                    System.out.println("배송");
                    validinput = true;
                    break;
                case 2:
                    System.out.println("고객센터");
                    validinput = true;
                    break;
                case 3:
                    System.out.println("회원정보");
                    validinput = true;
                    break;
                case 4:
                    System.out.println("로그아웃");
                    validinput = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    public static void adminMainMenu(String memberId) throws IOException {
        WMS inboundService = new WMS();
        System.out.println(memberId + " 님 환영합니다.");
        System.out.println("메인 메뉴 : 1.입고 | 2.출고 | 3.재고 | 4.재무 | 5.고객센터 | 6.회원정보 | 7.로그아웃");
        int select = SystemIn.SystemInInt();
        boolean validinput = false;
        while (!validinput) {
            switch (select) {
                case 1:
                    System.out.println("입고");
                    inboundService.serviceInbound();
                    validinput = true;
                    break;
                case 2:
                    System.out.println("출고");
                    validinput = true;
                    break;
                case 3:
                    System.out.println("재고");
                    validinput = true;
                    break;
                case 4:
                    System.out.println("재무");
                    validinput = true;
                    break;
                case 5:
                    System.out.println("고객센터");
                    validinput = true;
                    break;
                case 6:
                    System.out.println("회원정보");
                    memberServices.viewMemberInfo(memberId);
                    validinput = true;
                    break;
                case 7:
                    System.out.println("로그아웃");
                    validinput = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }

    }

    public static void showMenu(String memberId) throws SQLException, IOException {
        try {
            connection = ConnectionFactory.open();
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
                                System.out.println("Unknown member type.");
                                break;
                        }
                    } else {
                        System.out.println("Member type not found.");
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
