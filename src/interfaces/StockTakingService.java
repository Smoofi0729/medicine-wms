package interfaces;

import vo.StockTaking;

import java.sql.SQLException;
import java.util.List;

public interface StockTakingService {

    public StockTaking printStockTakingList(StockTaking stockTaking) throws SQLException;

    public void UpdateStockTakingList(StockTaking stockTaking) throws SQLException;

    public void insertStockTakingList(StockTaking stockTaking) throws SQLException;

    public void deleteStockTakingList(StockTaking stockTaking) throws SQLException;
}
