package dao.warehouse;

import dao.MysqlDBIO;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class WarehouseDBIO extends MysqlDBIO {

  @Override
  public Connection open() {
    return super.open();
  }

  @Override
  public PreparedStatement readyPstmt(String query) {
    return super.readyPstmt(query);
  }

  @Override
  public <T extends AutoCloseable> void close(T t) {
    super.close(t);
  }
}
