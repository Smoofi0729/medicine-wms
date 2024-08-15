package dao;

import static vo.MainMenu.showMenu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lombok.Data;

@Data
public abstract class ObjectDBIO {
  private String MYSQL_URL = "jdbc:mysql://localhost:3306/wms";
  private String MYSQL_ID = "smoofi";
  private String MYSQL_PW = "smoofi";
  private Connection connection;
  private PreparedStatement pstmt;

  public void startWMS() {
    open();
    showMenu();
    close(this.getConnection());
  }

  public PreparedStatement execute(String query) {
    try {
      pstmt = this.getConnection().prepareStatement(query);
      return pstmt;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Connection open() {
    try {
      return DriverManager.getConnection(this.getMYSQL_URL(), this.getMYSQL_ID(), this.getMYSQL_PW());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void close(Connection connection) {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
