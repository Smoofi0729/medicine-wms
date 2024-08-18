package interfaces;

import vo.Expenditure;

import java.sql.SQLException;

public interface ExpenditureService {
    public void printAllExpenditure() throws SQLException;

    public void insertExpenditure(Expenditure expenditure) throws SQLException;

    public void updateExpenditure(Expenditure expenditure) throws SQLException;

    public void deleteExpenditure(Expenditure expenditure) throws SQLException;

    public Expenditure printOneExpenditure(Expenditure expenditure) throws SQLException;
}
