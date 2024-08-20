package interfaces;

import vo.StockTaking;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface StockTakingService {

    Optional<StockTaking> printStockTakingList(StockTaking stockTaking) throws SQLException;

    public void UpdateStockTakingList(StockTaking stockTaking) throws SQLException;

    public void insertStockTakingList(StockTaking stockTaking) throws SQLException;

    public void deleteStockTakingList(StockTaking stockTaking) throws SQLException;
}
