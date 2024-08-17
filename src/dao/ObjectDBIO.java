package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ObjectDBIO {
  private Connection connection = null;
  private String db_url = "jdbc:mysql://localhost:3306/wms";
  private String db_id = "root";
  private String db_pwd = "mysql1234";
  private int n = 0;

  public boolean open() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(db_url, db_id, db_pwd);
      return true;
    } catch (ClassNotFoundException e) {
      System.err.println(e.getMessage());
      return false;
    } catch (SQLException e){
      System.err.println(e.getMessage());
      return false;
    }
  }

  protected boolean close(){
    try {
      connection.close();
      return true;
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return false;
    }
  }

  protected ResultSet execute(String query, ResultSet rs){
    try {
      open();
      Statement stmt = connection.createStatement();
      rs = stmt.executeQuery(query);
      //close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return  rs;
  }

  protected int execute(String query){
    try {
      open();
      Statement stmt = connection.createStatement();
      n = stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return  n;
  }



}

