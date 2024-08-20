package services;

import dao.StockTakingDao;
import interfaces.StockTakingService;
import java.sql.Connection;
import java.sql.SQLException;
import vo.StockTaking;
import java.util.List;
import java.util.Optional;

public class StockTakingServiceImpl implements StockTakingService {
    private StockTakingDao stockTakingDao;
    private StockTaking st;
    int result = 0;

    public StockTakingServiceImpl(Connection connection) {
        this.stockTakingDao = new StockTakingDao(connection);
    }

    @Override
    public Optional<StockTaking> printStockTakingList(StockTaking stockTaking) throws SQLException {
        st = stockTakingDao.selectStockTaking(stockTaking);
        //System.out.println(st.toString());
        return Optional.ofNullable(st);
    }

    @Override
    public void insertStockTakingList(StockTaking stockTaking) throws SQLException {
        result = stockTakingDao.insertStockTaking(stockTaking);

        if (result > 0) {
            System.out.println("재고 실사 목록이 추가되었습니다.");
        } else {
            System.out.println("재고 실사 목록 추가에 실패했습니다.");
        }
    }

    @Override
    public void UpdateStockTakingList(StockTaking stockTaking) throws SQLException {
        result = stockTakingDao.updateStockTaking(stockTaking);

        if (result > 0) {
            System.out.println("재고 실사 목록이 수정되었습니다.");
        } else {
            System.out.println("재고 실사 목록 수정에 실패했습니다.");
        }
    }

    @Override
    public void deleteStockTakingList(StockTaking stockTaking) throws SQLException {
        result = stockTakingDao.deleteStockTaking(stockTaking);

        if (result > 0) {
            System.out.println("재고 실사 목록이 삭제되었습니다.");
        } else {
            System.out.println("재고 실사 목록 삭제에 실패했습니다.");
        }
    }
}
