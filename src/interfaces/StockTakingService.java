package interfaces;

import vo.StockTaking;

import java.sql.SQLException;
import java.util.List;

public interface StockTakingService {

    public void printStockTakingList(List<StockTaking> stockTakings) throws SQLException;

    public void UpdateStockTakingList(List<StockTaking> stockTakings) throws SQLException;

    public void insertStockTakingList(List<StockTaking> stockTakings) throws SQLException;

    public void deleteStockTakingList(List<StockTaking> stockTakings) throws SQLException;
}
