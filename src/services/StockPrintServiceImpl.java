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
        System.out.println("LOT번호\t\t의약품이름\t수량\t상품id\t구역id\t\t창고이름");
        for (Stock stock : stocks) {
            System.out.println(stock.getProductLotNo() + "\t\t" + stock.getProductName() + "\t" + stock.getTotal() + "\t\t" + stock.getProductId() + "\t" + stock.getSectionId() + "\t" + stock.getWarehouseName());
        }
    }
}

