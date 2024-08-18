package services;

import dao.FinanceDao;
import interfaces.ExpenditureService;
import interfaces.RevenueService;
import vo.Expenditure;
import vo.Revenue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FinanceServiceImpl implements ExpenditureService, RevenueService {
    private FinanceDao financeDao;
    private Expenditure expenditure;
    int result = 0;

    public FinanceServiceImpl(Connection connection) {
        this.financeDao = new FinanceDao(connection);
    }

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
    public void insertExpenditure(Expenditure expenditure) throws SQLException {
        result = financeDao.insertExpenditure(expenditure);

        if (result > 0) {
            System.out.println("지출 내역이 추가되었습니다.");
        } else {
            System.out.println("지출 내역 추가에 실패했습니다.");
        }
    }

    @Override
    public void updateExpenditure(Expenditure expenditure) throws SQLException {
        result = financeDao.updateExpenditure(expenditure);

        if (result > 0) {
            System.out.println("지출 내역이 수정되었습니다.");
        } else {
            System.out.println("지출 내역 수정에 실패했습니다.");
        }
    }

    @Override
    public void deleteExpenditure(Expenditure expenditure) throws SQLException {
        result = financeDao.deleteExpenditure(expenditure);

        if (result > 0) {
            System.out.println("지출 내역이 삭제되었습니다.");
        } else {
            System.out.println("지출 내역 삭제에 실패했습니다.");
        }
    }

    @Override
    public Expenditure printOneExpenditure(Expenditure expenditure) throws SQLException {
        Expenditure ex = financeDao.selectOneExpenditure(expenditure);
        System.out.println(ex);
        return ex;
    }
}
