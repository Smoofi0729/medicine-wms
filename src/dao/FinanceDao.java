package dao;

import vo.Expenditure;
import vo.Revenue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FinanceDao {

    private final Connection connection;

    public FinanceDao(Connection connection) {
        this.connection = connection;
    }

    public List<Revenue> selectAllRevenue() throws SQLException {
        String query = "SELECT * FROM revenue";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            List<Revenue> revenues = new ArrayList<>();
            while (rs.next()) {
                revenues.add(createRevenueFromResultSet(rs));
            }
            return revenues;
        }
    }

    public List<Expenditure> selectAllExpenditure() throws SQLException {
        String query = "SELECT * FROM expenditure";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            List<Expenditure> expenditures = new ArrayList<>();
            while (rs.next()) {
                expenditures.add(createExpenditureFromResultSet(rs));
            }
            return expenditures;
        }
    }

    public int insertExpenditure(Expenditure expenditure) throws SQLException {
        String query = "INSERT INTO expenditure (expenditure_id, warehouse_id, expenditure_date, expenditure_charge, expenditure_category, note) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            setExpenditureParameters(pstmt, expenditure);
            return pstmt.executeUpdate();
        }
    }

    public int updateExpenditure(Expenditure expenditure) throws SQLException {
        String query = "UPDATE expenditure SET warehouse_id = ?, expenditure_date = ?, expenditure_charge = ?, expenditure_category = ?, note = ? WHERE expenditure_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            setExpenditureParameters(pstmt, expenditure);
            pstmt.setString(6, expenditure.getExpenditureId());
            return pstmt.executeUpdate();
        }
    }

    public int deleteExpenditure(Expenditure expenditure) throws SQLException {
        String query = "DELETE FROM expenditure WHERE expenditure_id = ? AND warehouse_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, expenditure.getExpenditureId());
            pstmt.setString(2, expenditure.getWarehouseId());
            return pstmt.executeUpdate();
        }
    }

    private Revenue createRevenueFromResultSet(ResultSet rs) throws SQLException {
        Revenue revenue = new Revenue();
        revenue.setRevenueId(rs.getString("revenue_id"));
        revenue.setWarehouseId(rs.getString("warehouse_id"));
        revenue.setRevenueDate(rs.getTimestamp("revenue_date"));
        revenue.setRevenueCharge(rs.getInt("revenue_charge"));
        revenue.setRevenueCategory(rs.getString("revenue_category"));
        revenue.setNote(rs.getString("note"));
        return revenue;
    }

    private Expenditure createExpenditureFromResultSet(ResultSet rs) throws SQLException {
        Expenditure expenditure = new Expenditure();
        expenditure.setExpenditureId(rs.getString("expenditure_id"));
        expenditure.setWarehouseId(rs.getString("warehouse_id"));
        expenditure.setExpenditureDate(rs.getDate("expenditure_date"));
        expenditure.setExpenditureCharge(rs.getInt("expenditure_charge"));
        expenditure.setExpenditureCategory(rs.getString("expenditure_category"));
        expenditure.setNote(rs.getString("note"));
        return expenditure;
    }

    private void setExpenditureParameters(PreparedStatement pstmt, Expenditure expenditure) throws SQLException {
        pstmt.setString(1, expenditure.getExpenditureId());
        pstmt.setString(2, expenditure.getWarehouseId());
        pstmt.setDate(3, new Date(expenditure.getExpenditureDate().getTime()));
        pstmt.setInt(4, expenditure.getExpenditureCharge());
        pstmt.setString(5, expenditure.getExpenditureCategory());
        pstmt.setString(6, expenditure.getNote());
    }
}