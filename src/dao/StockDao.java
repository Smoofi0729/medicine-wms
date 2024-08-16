package dao;

import vo.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDao {

    private final Connection connection;

    public StockDao(Connection connection) {
        this.connection = connection;
    }

    public List<Stock> selectAllStock() throws SQLException {
        return executeQuery("");
    }

    public List<Stock> selectByStorageStock() throws SQLException {
        return executeQuery("");
    }

    public List<Stock> selectByFormulationStock() throws SQLException {
        return executeQuery("");
    }

    private List<Stock> executeQuery(String query) throws SQLException {
        List<Stock> stocks = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(query);


        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            stocks.add(createStockFromResultSet(rs));
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