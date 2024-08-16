package services;

import dao.StockTakingDao;
import interfaces.StockTakingService;
import vo.Stock;
import vo.StockTaking;

import java.sql.SQLException;
import java.util.List;

public class StockTakingServiceImpl implements StockTakingService {
    private StockTakingDao stockTakingDao;

    @Override
    public void printStockTakingList() throws SQLException {
        stockTakingDao.selectStockTakingList();
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
