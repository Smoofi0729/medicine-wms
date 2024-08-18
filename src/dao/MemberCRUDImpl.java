package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MemberCRUDImpl {

    private Connection connection;

    public MemberCRUDImpl() {
        try {
            this.connection = ConnectionFactory.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View member information
    public void viewMember(String memberId) {
        String sql = "SELECT * FROM member WHERE member_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Common member info
                System.out.println("ID: " + rs.getString("member_id"));
                System.out.println("Name: " + rs.getString("member_name"));
                System.out.println("Phone Number: " + rs.getString("member_phoneNumber"));
                System.out.println("Email: " + rs.getString("member_email"));
                System.out.println("Address: " + rs.getString("member_address"));
                System.out.println("Type: " + rs.getString("member_type"));
                System.out.println("Status: " + rs.getString("member_status"));

                String memberType = rs.getString("member_type");

                // Display conditional fields based on member type
                switch (memberType) {
                    case "일반회원":
                        // General Member
                        System.out.println("Business Name: " + rs.getString("business_name"));
                        System.out.println("Business Address: " + rs.getString("business_address"));
                        break;

                    case "배송기사":
                        // Delivery Driver
                        System.out.println("Truck Type: " + rs.getString("truck_type"));
                        System.out.println("Truck Number: " + rs.getString("truck_number"));
                        break;

                    case "관리자":
                        // Administrator
                        System.out.println("Warehouse Name: " + rs.getString("warehouse_name"));
                        break;

                    default:
                        System.out.println("Unknown member type.");
                        break;
                }
            } else {
                System.out.println("Member not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Edit member information based on member type, including password
    public void editMember(String memberId, String newName, String newPhoneNumber, String newEmail, String newAddress, String newPassword) {
        String selectSql = "SELECT * FROM member WHERE member_id = ?";
        String memberType = null;
        String currentName = null;
        String currentPhoneNumber = null;
        String currentEmail = null;
        String currentAddress = null;
        String currentPassword = null;
        String currentBusinessName = null;
        String currentBusinessAddress = null;
        String currentTruckType = null;
        String currentTruckNumber = null;
        String currentWarehouseName = null;

        // Retrieve the current member information and type
        try (PreparedStatement selectPstmt = connection.prepareStatement(selectSql)) {
            selectPstmt.setString(1, memberId);
            try (ResultSet rs = selectPstmt.executeQuery()) {
                if (rs.next()) {
                    memberType = rs.getString("member_type");
                    currentName = rs.getString("member_name");
                    currentPhoneNumber = rs.getString("member_phoneNumber");
                    currentEmail = rs.getString("member_email");
                    currentAddress = rs.getString("member_address");
                    currentPassword = rs.getString("member_password");

                    // Retrieve type-specific fields
                    switch (memberType) {
                        case "일반회원":
                            currentBusinessName = rs.getString("business_name");
                            currentBusinessAddress = rs.getString("business_address");
                            break;

                        case "배송기사":
                            currentTruckType = rs.getString("truck_type");
                            currentTruckNumber = rs.getString("truck_number");
                            break;

                        case "관리자":
                            currentWarehouseName = rs.getString("warehouse_name");
                            break;
                    }
                } else {
                    System.out.println("Member not found.");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Use existing values if no input is provided
        newName = newName.isEmpty() ? currentName : newName;
        newPhoneNumber = newPhoneNumber.isEmpty() ? currentPhoneNumber : newPhoneNumber;
        newEmail = newEmail.isEmpty() ? currentEmail : newEmail;
        newAddress = newAddress.isEmpty() ? currentAddress : newAddress;
        newPassword = newPassword.isEmpty() ? currentPassword : newPassword;

        // Build the SQL query based on the member type
        StringBuilder sql = new StringBuilder("UPDATE member SET member_name = ?, member_phoneNumber = ?, member_email = ?, member_address = ?, member_password = ?");

        switch (memberType) {
            case "일반회원":
                sql.append(", business_name = ?, business_address = ?");
                break;

            case "배송기사":
                sql.append(", truck_type = ?, truck_number = ?");
                break;

            case "관리자":
                sql.append(", warehouse_name = ?");
                break;

            default:
                System.out.println("Invalid member type.");
                return;
        }

        sql.append(" WHERE member_id = ?");

        // Execute the update with the additional fields
        try (PreparedStatement pstmt = connection.prepareStatement(sql.toString())) {
            pstmt.setString(1, newName);
            pstmt.setString(2, newPhoneNumber);
            pstmt.setString(3, newEmail);
            pstmt.setString(4, newAddress);
            pstmt.setString(5, newPassword);

            Scanner scanner = new Scanner(System.in);

            // Set additional fields based on member type
            int parameterIndex = 6;
            switch (memberType) {
                case "일반회원":
                    System.out.print("새 사업자명을 입력하세요 (그대로 두려면 Enter): ");
                    String newBusinessName = scanner.nextLine();
                    pstmt.setString(parameterIndex++, newBusinessName.isEmpty() ? currentBusinessName : newBusinessName);

                    System.out.print("새 사업장 주소를 입력하세요 (그대로 두려면 Enter): ");
                    String newBusinessAddress = scanner.nextLine();
                    pstmt.setString(parameterIndex++, newBusinessAddress.isEmpty() ? currentBusinessAddress : newBusinessAddress);
                    break;

                case "배송기사":
                    System.out.print("새 트럭 종류를 입력하세요 (그대로 두려면 Enter): ");
                    String newTruckType = scanner.nextLine();
                    pstmt.setString(parameterIndex++, newTruckType.isEmpty() ? currentTruckType : newTruckType);

                    System.out.print("새 트럭 번호를 입력하세요 (그대로 두려면 Enter): ");
                    String newTruckNumber = scanner.nextLine();
                    pstmt.setString(parameterIndex++, newTruckNumber.isEmpty() ? currentTruckNumber : newTruckNumber);
                    break;

                case "관리자":
                    System.out.print("새 창고명을 입력하세요 (그대로 두려면 Enter): ");
                    String newWarehouseName = scanner.nextLine();
                    pstmt.setString(parameterIndex++, newWarehouseName.isEmpty() ? currentWarehouseName : newWarehouseName);
                    break;
            }

            pstmt.setString(parameterIndex, memberId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("회원정보가 수정되었습니다.");
            } else {
                System.out.println("회원정보 수정이 실패했습니다");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Request deletion of member information
    public void requestDeletion(String memberId) {
        String sql = "UPDATE member SET member_status = '비활성 요청' WHERE member_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("삭제 요청이 완료되었습니다. 관리자 승인 대기중입니다.");
            } else {
                System.out.println("Deletion request failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Admin approve deletion request
    public void approveDeletion(String memberId) {
        String sql = "UPDATE member SET member_status = '비활성', approval = 'true' WHERE member_id = ? AND approval = 'pending'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Deletion approved. Member status is now inactive.");
            } else {
                System.out.println("Approval failed or no pending deletion request found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Close the connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
