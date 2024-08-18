package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Data;

@Data
public abstract class ObjectDBIO {

  private String MYSQL_URL = "jdbc:mysql://localhost:3306/wms";
  private String MYSQL_ID = "ssg";
  private String MYSQL_PW = "ssg";
  private Connection connection;
  private PreparedStatement pstmt;
  private ResultSet rs;


  public Connection open() {
    try {
      this.connection = DriverManager.getConnection(this.getMYSQL_URL(), this.getMYSQL_ID(),
          this.getMYSQL_PW());
      return this.connection;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public PreparedStatement readyPstmt(String query) {
    try  {
      this.pstmt = getConnection().prepareStatement(query);
      return this.pstmt;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public <T extends AutoCloseable> void close(T t) {
    try {
      t.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}