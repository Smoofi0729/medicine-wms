package interfaces;


import vo.Stock;

import java.sql.SQLException;
import java.util.List;

public interface StockPrintService {
    public void printAllStock() throws SQLException;

    public void printByStorageStock() throws SQLException;

    public void printByFormulationStock() throws SQLException;
}
