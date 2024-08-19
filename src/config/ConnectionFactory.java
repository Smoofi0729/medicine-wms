package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final ConnectionFactory instance = new ConnectionFactory();

    private ConnectionFactory() {}

    public static ConnectionFactory getInstance() {
        return instance;
    }

    private static Connection connection = null;
    private static String url = "jdbc:mysql://localhost:3306/wms?serverTimezone=Asia/Seoul";
    private static String id = "root";
    private static String pwd = "1234";

    public static Connection open() {
        try {
            connection = DriverManager.getConnection(url, id, pwd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    public void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
