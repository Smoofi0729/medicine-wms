package dao;

import vo.StockTaking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockTakingDao {
    private final Connection connection;

    public StockTakingDao(Connection connection) {
        this.connection = connection;
    }

    public StockTaking selectStockTaking(StockTaking st) throws SQLException {
        String query = "SELECT * FROM stock_taking WHERE stocktaking_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, st.getStockTakingId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return createStockTakingFromResultSet(rs);
                } else {
                    return null;
                }
            }
        }
    }

    public int insertStockTaking(StockTaking stockTaking) throws SQLException {
        String query = "INSERT INTO stock_taking VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            setStockTakingInsert(pstmt, stockTaking);
            return pstmt.executeUpdate();
        }
    }

    public int updateStockTaking(StockTaking stockTaking) throws SQLException {
        String query = "UPDATE stock_taking SET computerized_stock = ?, physical_stock = ?, difference_quantity = ?, note = ? WHERE stocktaking_id = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            setStockTakingUpdate(pstmt, stockTaking);
            pstmt.setString(5, stockTaking.getStockTakingId());
            return pstmt.executeUpdate();
        }
    }

    public int deleteStockTaking(StockTaking stockTaking) throws SQLException {
        String query = "DELETE FROM stock_taking WHERE stocktaking_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stockTaking.getStockTakingId());
            return pstmt.executeUpdate();
        }
    }

    private StockTaking createStockTakingFromResultSet(ResultSet rs) throws SQLException {
        StockTaking stockTaking = new StockTaking();
        stockTaking.setStockTakingId(rs.getString("stocktaking_id"));
        stockTaking.setWarehouseId(rs.getString("warehouse_id"));
        stockTaking.setProductId(rs.getString("product_id"));
        stockTaking.setProductName(rs.getString("product_name"));
        stockTaking.setLotNo(rs.getString("lotno"));
        stockTaking.setComputerizedStock(rs.getInt("computerized_stock"));
        stockTaking.setPhysicalStock(rs.getInt("physical_stock"));
        stockTaking.setDifferenceQuantity(rs.getInt("difference_quantity"));
        stockTaking.setStockTakingDate(rs.getTimestamp("stocktaking_date"));
        stockTaking.setNote(rs.getString("note"));
        return stockTaking;
    }

    private void setStockTakingUpdate(PreparedStatement pstmt, StockTaking stockTaking) throws SQLException {
        pstmt.setInt(1, stockTaking.getComputerizedStock());
        pstmt.setInt(2, stockTaking.getPhysicalStock());
        pstmt.setInt(3, stockTaking.getPhysicalStock());
        pstmt.setString(4, stockTaking.getNote());
    }

    private void setStockTakingInsert(PreparedStatement pstmt, StockTaking stockTaking) throws SQLException {
        pstmt.setString(1, stockTaking.getStockTakingId());
        pstmt.setString(2, stockTaking.getWarehouseId());
        pstmt.setString(3, stockTaking.getProductId());
        pstmt.setString(4, stockTaking.getProductName());
        pstmt.setString(5, stockTaking.getLotNo());
        pstmt.setInt(6, stockTaking.getComputerizedStock());
        pstmt.setInt(7, stockTaking.getPhysicalStock());
        pstmt.setInt(8, stockTaking.getDifferenceQuantity());
        pstmt.setTimestamp(9, new Timestamp(stockTaking.getStockTakingDate().getTime()));
        pstmt.setString(10, stockTaking.getNote());
    }
}