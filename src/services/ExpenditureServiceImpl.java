package services;

import dao.FinanceDao;
import interfaces.ExpenditureService;
import interfaces.RevenueService;
import vo.Expenditure;
import vo.Revenue;

import java.sql.SQLException;
import java.util.List;

public class ExpenditureServiceImpl implements ExpenditureService, RevenueService {
    private FinanceDao financeDao;
    private Expenditure expenditure;

    @Override
    public void printAllRevenue() throws SQLException {
        List<Revenue> revenues = financeDao.selectAllRevenue();
        revenues.forEach(revenue -> System.out.println(revenue));
    }

    @Override
    public void printAllExpenditure() throws SQLException {
        List<Expenditure> expenditures = financeDao.selectAllExpenditure();
        expenditures.forEach(expenditure -> System.out.println(expenditure));
    }

    @Override
    public void insertExpenditure() throws SQLException {
        financeDao.insertExpenditure(expenditure);
    }

    @Override
    public void updateExpenditure() throws SQLException {
        financeDao.updateExpenditure(expenditure);
    }

    @Override
    public void deleteExpenditure() throws SQLException {
        financeDao.deleteExpenditure(expenditure);
    }
}
