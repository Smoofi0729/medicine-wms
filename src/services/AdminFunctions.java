package services;

import dao.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminFunctions {

    private static Connection connection;

    static {
        connection = ConnectionFactory.open();
    }

    public static void searchAllMembers() throws SQLException {
        String sql = "SELECT * FROM member";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            printMemberResults(rs);
        }
    }

    public static void searchMembersByType(String type) throws SQLException {
        String sql = "SELECT * FROM member WHERE member_type = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                printMemberResults(rs);
            }
        }
    }

    public static void searchMemberById(String memberId) throws SQLException {
        String sql = "SELECT * FROM member WHERE member_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                printMemberResults(rs);
            }
        }
    }

    public static void searchMembersWithPendingApproval() throws SQLException {
        String sql = "SELECT * FROM member WHERE approval = 'false'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            printMemberResults(rs);
        }
    }

    public static void approveMember(String memberId) throws SQLException {
        String sql = "UPDATE member SET approval = 'true' WHERE member_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("회원 권한이 승인되었습니다");
            } else {
                System.out.println("일치하는 ID가 없습니다.");
            }
        }
    }

    private static void printMemberResults(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println("ID: " + rs.getString("member_id"));
            System.out.println("이름: " + rs.getString("member_name"));
            System.out.println("전하번호: " + rs.getString("member_phoneNumber"));
            System.out.println("Email: " + rs.getString("member_email"));
            System.out.println("회원정보: " + rs.getString("member_type"));
            System.out.println("접근 승인: " + rs.getString("approval"));
            System.out.println("-------------------------------------");
        }
    }
}