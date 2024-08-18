package services;

import dao.StockTakingDao;
import interfaces.StockTakingService;
import vo.Stock;
import vo.StockTaking;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StockTakingServiceImpl implements StockTakingService {
    private StockTakingDao stockTakingDao;
    private StockTaking st;
    int result = 0;

    public StockTakingServiceImpl(Connection connection) {
        this.stockTakingDao = new StockTakingDao(connection);
    }

    @Override
    public void printStockTakingList(StockTaking stockTaking) throws SQLException {
         st = stockTakingDao.selectStockTaking(stockTaking);
        System.out.println(st.toString());
    }

    @Override
    public void insertStockTakingList(StockTaking stockTaking) throws SQLException {
        result =  stockTakingDao.insertStockTaking(stockTaking);
    }

    @Override
    public void UpdateStockTakingList(StockTaking stockTaking) throws SQLException {
        result = stockTakingDao.updateStockTaking(stockTaking);
    }

    @Override
    public void deleteStockTakingList(StockTaking stockTaking) throws SQLException {
        result = stockTakingDao.deleteStockTaking(stockTaking);
    }
}
