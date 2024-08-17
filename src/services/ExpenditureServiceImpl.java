package services;

import dao.FinanceDao;
import interfaces.ExpenditureService;

import java.sql.SQLException;

public class ExpenditureServiceImpl implements ExpenditureService {
    private FinanceDao financeDao;

    @Override
    public void printAllExpenditure() throws SQLException {
        financeDao.selectAllExpenditure();
    }

    @Override
    public void insertExpenditure() throws SQLException {
        financeDao.insertExpenditure();
    }

    @Override
    public void updateExpenditure() throws SQLException {
        financeDao.updateExpenditure();
    }

    @Override
    public void deleteExpenditure() throws SQLException {
        financeDao.deleteExpenditure();
    }
}
