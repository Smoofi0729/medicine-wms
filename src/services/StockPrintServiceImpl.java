package services;

import dao.StockDao;
import interfaces.StockPrintService;
import vo.Stock;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StockPrintServiceImpl implements StockPrintService {
    private StockDao stockDao;

    public StockPrintServiceImpl(Connection connection) {
        this.stockDao = new StockDao(connection);
    }

    @Override
    public void printAllStock() throws SQLException {
        printStocks(stockDao.selectAllStock());
    }

    @Override
    public void printByStorageStock() throws SQLException {
        printStocks(stockDao.selectByStorageStock());
    }

    @Override
    public void printByFormulationStock() throws SQLException {
        printStocks(stockDao.selectByFormulationStock());
    }

    public void printStocks(List<Stock> stocks) {
        stocks.forEach(stock -> stock.toString());
    }
}
