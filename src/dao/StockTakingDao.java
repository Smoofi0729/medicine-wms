package dao;

import vo.Stock;
import vo.StockTaking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//커넥션 연결은 아직 안된 상태
public class StockTakingDao {
    private final Connection connection;
    private StockTaking stockTaking;

    public StockTakingDao(Connection connection) {
        this.connection = connection;
    }

    public List<StockTaking> selectStockTakingList() throws SQLException {
        return executeQuery("SELECT * FROM stock_taking");
    }

    public int insertStockTakingList(List<StockTaking> stockTakings) throws SQLException {
        return executeQuery("INSERT INTO stock_taking VALUES (?,?,?,?,?,?,?,?,?,?,?)", stockTaking);
    }

    //set ~~ 추가해야함
    public int updateStockTakingList(List<StockTaking> stockTakings) throws SQLException {
        return executeQuery("UPDATE stock_taking SET ~~ WHERE stocktaking_id = ?", stockTaking); //내부 수정해야함
    }

    public int deleteStockTakingList(List<StockTaking> stockTakings) throws SQLException {
        return executeQuery("DELETE FROM stock_taking WHERE stocktaking_id = ?", stockTaking);
    }

    private List<StockTaking> executeQuery(String query) throws SQLException {
        List<StockTaking> stockTakings = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(query);


        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            stockTakings.add(createStockTakingFromResultSet(rs));
        }

        return stockTakings;
    }

    //내부 수정해야함
    private int executeQuery(String query, StockTaking Taking) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, stockTaking.getProductId());
        pstmt.setString(2, stockTaking.getProductName());
        pstmt.setInt(3, stockTaking.getTotal());
        pstmt.setString(4, stockTaking.getLotNo());
        pstmt.setString(5, stockTaking.getUnit());
        pstmt.setInt(6, stockTaking.getComputerizedStock());
        pstmt.setInt(7, stockTaking.getPhysicalStock());
        pstmt.setInt(8, stockTaking.getDifferenceQuantity());
        pstmt.setDate(9, (Date) stockTaking.getStockTakingDate());
        pstmt.setDate(10, (Date) stockTaking.getStockTakingDate());

        return pstmt.executeUpdate();
    }

    //내부 수정해야함
    private StockTaking createStockTakingFromResultSet(ResultSet rs) throws SQLException {
        StockTaking st = new StockTaking();

        st.setStockTakingId(rs.getString("stocktaking_id"));
        st.setWarehouseId(rs.getString("warehouse_id"));
        st.setProductId(rs.getString("product_id"));
        st.setProductName(rs.getString("product_name"));
        st.setTotal(rs.getInt("total"));
        st.setLotNo(rs.getString("lotno"));
        st.setUnit(rs.getString("unit"));
        st.setComputerizedStock(rs.getInt("computerized_stock"));
        st.setPhysicalStock(rs.getInt("physical_stock"));
        st.setDifferenceQuantity(rs.getInt("difference_quantity"));
        st.setStockTakingDate(rs.getDate("stocktaking_date")); //sql 추가 해야함
        st.setNote(rs.getString("note"));

        return stockTaking;
    }
}
