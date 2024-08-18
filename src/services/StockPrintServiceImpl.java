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
        this.stockDao = new StockDao();
    }

    @Override
    public void printAllStock() throws SQLException {
        printStocks(stockDao.selectAllStock());
    }

    @Override
    public void printBySectionStock(Stock stock) throws SQLException {
        printStocks(stockDao.selectBySectionStock(stock));
    }

    public void printStocks(List<Stock> stocks) {
        stocks.forEach(System.out::println);
    }
}

