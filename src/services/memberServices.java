package services;

import config.CLIController;
import config.ConnectionFactory;
import config.SystemIn;
import dao.MemberCRUDImpl;
import vo.UserMessege;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class memberServices {
    private static Connection connection; // Changed to non-static

    public static void signInMember() throws Exception { // Changed to non-static
        try {
            connection = ConnectionFactory.getInstance().open();
            UserMessege.SIGN_UP.println();
            UserMessege.ID.println();
            String memberId = SystemIn.SystemInString();

            UserMessege.SIGN_UP_Name.println();
            String memberName = SystemIn.SystemInString();

            UserMessege.PASSWORD.println();
            String memberPassword = SystemIn.SystemInString();

            UserMessege.CONTACT.println();
            String memberPhoneNumber = SystemIn.SystemInString();

            UserMessege.SIGN_UP_EMAIL.println();
            String memberEmail = SystemIn.SystemInString();

            UserMessege.SIGN_UP_ADDRESS.println();
            String memberAddress = SystemIn.SystemInString();

            String memberType = null;
            String approval = "false";
            String businessName = null;
            String businessAddress = null;
            String warehouseName = null;
            String truckType = null;
            String truckNumber = null;
            String memberStatus = "활성";

            boolean validInputType = false;

            while (!validInputType) {
                UserMessege.SIGN_UP_MEMBER_TYPE.println();
                switch (SystemIn.SystemInInt()) {
                    case 1:
                        memberType = "일반회원";
                        UserMessege.SIGN_UP_BUSINESS_NAME.println();
                        businessName = SystemIn.SystemInString();
                        UserMessege.SIGN_UP_BUSINESS_NUMER.println();
                        businessAddress = SystemIn.SystemInString();
                        validInputType = true;
                        break;
                    case 2:
                        memberType = "배송기사";
                        boolean validInput = false;
                        while (!validInput) {
                            UserMessege.SIGN_UP_TRUCK_FUNCTION.println();
                            switch (SystemIn.SystemInString()) {
                                case "1":
                                    truckType = "냉장";
                                    validInput = true;
                                    break;
                                case "2":
                                    truckType = "냉동";
                                    validInput = true;
                                    break;
                                case "3":
                                    truckType = "일반";
                                    validInput = true;
                                    break;
                                default:
                                    System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요");
                            }
                        }
                        UserMessege.SIGN_UP_TRUCK_NUMBER.println();
                        truckNumber = SystemIn.SystemInString();
                        validInputType = true;
                        break;
                    case 3:
                        memberType = "관리자";
                        UserMessege.SIGN_UP_WAREHOUSE_NAME.println();
                        warehouseName = SystemIn.SystemInString();
                        validInputType = true;
                        break;
                    default:
                        System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요.");
                }
            }

            String sql = "INSERT INTO member (member_id, member_name, member_password, member_phoneNumber, member_email, "
                    + "member_address, member_type, member_status, approval, createAt, lastLogin, business_name, "
                    + "business_address, warehouse_name, truck_type, truck_number) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, memberId);
                pstmt.setString(2, memberName);
                pstmt.setString(3, memberPassword);
                pstmt.setString(4, memberPhoneNumber);
                pstmt.setString(5, memberEmail);
                pstmt.setString(6, memberAddress);
                pstmt.setString(7, memberType);
                pstmt.setString(8, memberStatus);
                pstmt.setString(9, approval);
                pstmt.setString(10, businessName);
                pstmt.setString(11, businessAddress);
                pstmt.setString(12, warehouseName);
                pstmt.setString(13, truckType);
                pstmt.setString(14, truckNumber);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("회원가입 성공.");
                } else {
                    System.out.println("회원가입 실패.");
                }
                new CLIController().BasicMenu(); // Changed to non-static call
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

    public static boolean login() throws SQLException { // Changed to non-static
        try {
            connection = ConnectionFactory.getInstance().open();
            UserMessege.LOGIN.println();
            UserMessege.ID.println();
            String userid = SystemIn.SystemInString();

            UserMessege.PASSWORD.println();
            String password = SystemIn.SystemInString();

            String sql = "SELECT member_id, approval FROM member WHERE member_id = ? AND member_password = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, userid);
                pstmt.setString(2, password);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String approval = rs.getString("approval");
                        if ("true".equalsIgnoreCase(approval)) {
                            System.out.print(userid + " ");
                            UserMessege.MENU_WELCOME.println();
                            new CLIController().showMenu(userid); // Changed to non-static call
                            return true;
                        } else {
                            System.out.println("승인 대기중입니다. 관리자에게 문의하세요!");
                            return false;
                        }
                    } else {
                        UserMessege.LOGIN_ERROR.println();
                        login();
                        return false;
                    }
                }
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
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

    public static String findId() throws SQLException, IOException { // Changed to non-static
        try {
            connection = ConnectionFactory.getInstance().open();

            UserMessege.SIGN_UP_Name.println();
            String name = SystemIn.SystemInString();

            UserMessege.CONTACT.println();
            String phoneNumber = SystemIn.SystemInString();

            String sql = "SELECT member_id FROM member WHERE member_name = ? AND member_phoneNumber = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, phoneNumber);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String memberId = rs.getString("member_id");
                        System.out.println("회원님의 ID는 \"" + memberId + "\" 입니다.");
                        return memberId;
                    } else {
                        System.out.println("회원정보를 찾을 수 없습니다.");
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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

    public static boolean resetPassword() throws IOException { // Changed to non-static
        try {
            connection = ConnectionFactory.getInstance().open();

            UserMessege.ID.println();
            String memberId = SystemIn.SystemInString();

            UserMessege.SIGN_UP_Name.println();
            String name = SystemIn.SystemInString();

            UserMessege.SIGN_UP_EMAIL.println();
            String email = SystemIn.SystemInString();

            String sql = "SELECT member_id FROM member WHERE member_id = ? AND member_name = ? AND member_email = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, memberId);
                pstmt.setString(2, name);
                pstmt.setString(3, email);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        UserMessege.NEW_PASSWORD.println();
                        String newPassword = SystemIn.SystemInString();

                        String updateSql = "UPDATE member SET member_password = ? WHERE member_id = ?";
                        try (PreparedStatement updatePstmt = connection.prepareStatement(updateSql)) {
                            updatePstmt.setString(1, newPassword);
                            updatePstmt.setString(2, memberId);

                            int rowsAffected = updatePstmt.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("비밀번호가 성공적으로 변경되었습니다.");
                                return true;
                            } else {
                                System.out.println("비밀번호 변경에 실패했습니다.");
                                return false;
                            }
                        }
                    } else {
                        System.out.println("회원정보를 찾을 수 없습니다.");
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
    public void logout(String memberId) {
        // Perform any necessary cleanup (if needed)
        System.out.println("회원 " + memberId + " 님이 로그아웃되었습니다.");

        // Optionally, redirect to the basic menu
        try {
            new CLIController().BasicMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewMemberInfo(String memberId) throws IOException { // Changed to non-static
        boolean validinput = false;
        MemberCRUDImpl memberCRUD = new MemberCRUDImpl();

        System.out.println("1. 회원정보 조회 | 2. 회원정보 수정 | 3. 회원 탈퇴");
        int select = SystemIn.SystemInInt();

        while (!validinput) {
            switch (select) {
                case 1:
                    memberCRUD.viewMember(memberId);
                    validinput = true;
                    break;

                case 2:
                    String editMemberId = memberId;

                    System.out.print("새 이름을 입력하세요 (그대로 두려면 Enter): ");
                    String newName = SystemIn.SystemInString();

                    System.out.print("새 전화번호를 입력하세요 (그대로 두려면 Enter): ");
                    String newPhoneNumber = SystemIn.SystemInString();

                    System.out.print("새 이메일을 입력하세요 (그대로 두려면 Enter): ");
                    String newEmail = SystemIn.SystemInString();

                    System.out.print("새 주소를 입력하세요 (그대로 두려면 Enter): ");
                    String newAddress = SystemIn.SystemInString();

                    System.out.print("새 비밀번호를 입력하세요 (그대로 두려면 Enter): ");
                    String newPassword = SystemIn.SystemInString();

                    memberCRUD.editMember(editMemberId, newName, newPhoneNumber, newEmail, newAddress, newPassword);
                    validinput = true;
                    break;

                case 3:
                    System.out.println("회원을 탈퇴하시겠습니까");
                    System.out.println("1. YES | 2. NO ");
                    int selectNum = SystemIn.SystemInInt();
                    if (selectNum == 1) {
                        memberCRUD.requestDeletion(memberId);
                    }
                    validinput = true;
                    break;

                default:
                    System.out.println("잘못된 입력입니다. 다시 한번 입력해주세요.");
            }

            memberCRUD.closeConnection();
        }
    }
}
