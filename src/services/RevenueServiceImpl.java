package services;

import dao.RevenueDao;
import interfaces.RevenueService;
import vo.Revenue;

import java.sql.Connection;
import java.sql.SQLException;

public class RevenueServiceImpl implements RevenueService {
    private RevenueDao revenueDao;

    public RevenueServiceImpl(Connection connection) {
        this.revenueDao = new RevenueDao(connection);
    }

    @Override
    public void printAllRevenue() throws SQLException {
        revenueDao.selectAllRevenue();
    }
}
