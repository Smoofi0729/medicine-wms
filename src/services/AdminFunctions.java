package services;

import config.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminFunctions {

    private Connection connection;

    public AdminFunctions() {
        this.connection = ConnectionFactory.getInstance().open();
    }

    public void searchAllMembers() throws SQLException { 
        String sql = "SELECT * FROM member";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            printMemberResults(rs);
        }
    }

    public void searchMembersByType(String type) throws SQLException { 
        String sql = "SELECT * FROM member WHERE member_type = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                printMemberResults(rs);
            }
        }
    }

    public void searchMemberById(String memberId) throws SQLException {
        String sql = "SELECT * FROM member WHERE member_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                printMemberResults(rs);
            }
        }
    }

    public void searchMembersWithPendingApproval() throws SQLException {
        String sql = "SELECT * FROM member WHERE approval = 'false'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            printMemberResults(rs);
        }
    }

    public void approveMember(String memberId) throws SQLException { 
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
    public void finalizeMemberDeletion(String memberId) throws SQLException {
        String sql = "UPDATE member SET member_status = '비활성', approval = 'true' WHERE member_id = ? AND member_status = '비활성 요청'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("회원의 상태가 '비활성'으로 변경되었습니다.");
            } else {
                System.out.println("비활성 요청이 없거나, 삭제에 실패했습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printMemberResults(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println("ID: " + rs.getString("member_id"));
            System.out.println("이름: " + rs.getString("member_name"));
            System.out.println("전화번호: " + rs.getString("member_phoneNumber"));
            System.out.println("Email: " + rs.getString("member_email"));
            System.out.println("회원정보: " + rs.getString("member_type"));
            System.out.println("접근 승인: " + rs.getString("approval"));
            System.out.println("-------------------------------------");
        }
    }
}
