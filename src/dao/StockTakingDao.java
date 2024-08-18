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
        String query = "INSERT INTO stock_taking VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            setStockTakingParameters(pstmt, stockTaking);
            return pstmt.executeUpdate();
        }
    }

    public int updateStockTaking(StockTaking stockTaking) throws SQLException {
        String query = "UPDATE stock_taking SET warehouse_id = ?, product_id = ?, product_name = ?, total = ?, lotno = ?, computerized_stock = ?, physical_stock = ?, difference_quantity = ?, stocktaking_date = ?, note = ? WHERE stocktaking_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            setStockTakingParameters(pstmt, stockTaking);
            pstmt.setString(12, stockTaking.getStockTakingId());
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
        stockTaking.setTotal(rs.getInt("total"));
        stockTaking.setLotNo(rs.getString("lotno"));
        stockTaking.setComputerizedStock(rs.getInt("computerized_stock"));
        stockTaking.setPhysicalStock(rs.getInt("physical_stock"));
        stockTaking.setDifferenceQuantity(rs.getInt("difference_quantity"));
        stockTaking.setStockTakingDate(rs.getTimestamp("stocktaking_date"));
        stockTaking.setNote(rs.getString("note"));
        return stockTaking;
    }

    private void setStockTakingParameters(PreparedStatement pstmt, StockTaking stockTaking) throws SQLException {
        pstmt.setString(1, stockTaking.getStockTakingId());
        pstmt.setString(2, stockTaking.getWarehouseId());
        pstmt.setString(3, stockTaking.getProductId());
        pstmt.setString(4, stockTaking.getProductName());
        pstmt.setInt(5, stockTaking.getTotal());
        pstmt.setString(6, stockTaking.getLotNo());
        pstmt.setInt(7, stockTaking.getComputerizedStock());
        pstmt.setInt(8, stockTaking.getPhysicalStock());
        pstmt.setInt(9, stockTaking.getDifferenceQuantity());
        pstmt.setTimestamp(10, new Timestamp(stockTaking.getStockTakingDate().getTime()));
        pstmt.setString(11, stockTaking.getNote());
    }
}