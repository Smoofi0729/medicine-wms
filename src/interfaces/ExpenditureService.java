package interfaces;

import java.sql.SQLException;

public interface ExpenditureService {
    public void printAllExpenditure() throws SQLException;

    public void insertExpenditure() throws SQLException;

    public void updateExpenditure() throws SQLException;

    public void deleteExpenditure() throws SQLException;
}
