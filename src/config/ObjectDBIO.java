package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Data;

@Data
public abstract class ObjectDBIO {



  public abstract Connection open();

  public <T extends AutoCloseable> void close(T t) {
    try {
      t.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
