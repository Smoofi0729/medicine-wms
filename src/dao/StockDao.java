package dao;

import config.ConnectionFactory;
import vo.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDao {

    private final Connection connection;
    private Stock stock;
    private String defaultQuery = "select st.product_lotno, p.product_name, st.section_id, w.warehouse_name, st.total, st.expiration_date from stock st, warehouse w, product p, section se\n" +
            "where st.product_id = p.product_id and st.section_id = se.section_id and w.warehouse_id = st.warehouse_id";

    public StockDao() {
        this.connection = ConnectionFactory.getInstance().open();
    }

    public List<Stock> selectAllStock() throws SQLException {
        return executeQuery(defaultQuery + ";");
    }

    public List<Stock> selectBySectionStock(Stock stock) throws SQLException {
        return executeQuery(defaultQuery + " where st.section_id = ?;", stock);
    }

    private List<Stock> executeQuery(String query) throws SQLException {
        List<Stock> stocks = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                stocks.add(createStockFromResultSet(rs));
            }
        }
        return stocks;
    }

    private List<Stock> executeQuery(String query, Stock st) throws SQLException {
        List<Stock> stocks = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, st.getSectionId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    stocks.add(createStockFromResultSet(rs));
                }
            }
        }
        return stocks;
    }

    private Stock createStockFromResultSet(ResultSet rs) throws SQLException {
        Stock stock = new Stock();
        stock.setProductLotNo(rs.getString("product_lotno"));
        stock.setProductName(rs.getString("product_name"));
        stock.setUnit(rs.getString("unit"));
        stock.setTotal(rs.getInt("total"));
        stock.setExpirationDate(rs.getInt("expiration_date"));
        stock.setProductId(rs.getString("product_id"));
        stock.setWarehouseId(rs.getString("warehouse_id"));
        stock.setSectionId(rs.getString("section_id"));
        return stock;
    }
}