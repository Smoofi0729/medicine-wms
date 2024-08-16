package dao;

import vo.Revenue;
import vo.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RevenueDao {
    private final Connection connection;

    public RevenueDao(Connection connection) {
        this.connection = connection;
    }

    public List<Revenue> selectAllRevenue() throws SQLException {
        return executeQuery("select * from revenue");
    }

    private List<Revenue> executeQuery(String query) throws SQLException {
        List<Revenue> revs = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(query);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            revs.add(createRevenueFromResultSet(rs));
        }

        return revs;
    }

    private Revenue createRevenueFromResultSet(ResultSet rs) throws SQLException {
        Revenue revenue = new Revenue();
        //setting
        return revenue;
    }
}
