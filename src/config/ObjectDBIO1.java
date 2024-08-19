package config;

import java.sql.Connection;

import lombok.Data;

@Data
public abstract class ObjectDBIO1 {



  public abstract Connection open();

  public <T extends AutoCloseable> void close(T t) {
    try {
      t.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
