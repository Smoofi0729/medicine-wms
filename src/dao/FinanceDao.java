package dao;

import vo.Expenditure;
import vo.Stock;
import vo.StockTaking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FinanceDao {

    private final Connection connection;
    private Expenditure expenditure;

    public FinanceDao(Connection connection) {
        this.connection = connection;
    }

    public List<Expenditure> selectAllExpenditure() throws SQLException {
        return executeQuery("");
    }

    public int insertExpenditure() throws SQLException {
        return executeQuery("", expenditure);
    }

    public int updateExpenditure() throws SQLException {
        return executeQuery("", expenditure);
    }

    public int deleteExpenditure() throws SQLException {
        return executeQuery("", expenditure);

    }

    private List<Expenditure> executeQuery(String query) throws SQLException {
        List<Expenditure> exps = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(query);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            exps.add(createExpenditureFromResultSet(rs));
        }

        return exps;
    }

    private int executeQuery(String query, Expenditure expenditure) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(query);
        //setting
        return pstmt.executeUpdate();
    }

    private Expenditure createExpenditureFromResultSet(ResultSet rs) throws SQLException {
        Expenditure exp = new Expenditure();
        //setting
        return exp;
    }
}
