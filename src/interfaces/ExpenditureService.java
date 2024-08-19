package interfaces;

import vo.Expenditure;

import java.sql.SQLException;
import java.util.Optional;

public interface ExpenditureService {
    public void printAllExpenditure() throws SQLException;

    public void insertExpenditure(Expenditure expenditure) throws SQLException;

    public void updateExpenditure(Expenditure expenditure) throws SQLException;

    public void deleteExpenditure(Expenditure expenditure) throws SQLException;

    Optional<Expenditure> printOneExpenditure(Expenditure expenditure) throws SQLException;
}
