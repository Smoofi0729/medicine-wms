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

    public StockTakingServiceImpl(Connection connection) {
        this.stockTakingDao = new StockTakingDao(connection);
    }

    @Override
    public void printStockTakingList(List<StockTaking> stockTakings) throws SQLException {
        stockTakingDao.selectStockTakingList(stockTakings);
    }

    @Override
    public void insertStockTakingList(List<StockTaking> stockTakings) throws SQLException {
        stockTakingDao.insertStockTakingList(stockTakings);
    }

    @Override
    public void UpdateStockTakingList(List<StockTaking> stockTakings) throws SQLException {
        stockTakingDao.updateStockTakingList(stockTakings);
    }

    @Override
    public void deleteStockTakingList(List<StockTaking> stockTakings) throws SQLException {
        stockTakingDao.deleteStockTakingList(stockTakings);
    }
}
