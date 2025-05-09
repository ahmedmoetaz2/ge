package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    private static MyDataBase instance;
    private Connection cnx;

    // Database name: Use underscores, no spaces
    private static final String URL = "jdbc:mysql://localhost:3306/event planner";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private MyDataBase() {
        try {
            establishConnection();
            System.out.println("Database connected!");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    public static synchronized MyDataBase getInstance() {
        if (instance == null) {
            instance = new MyDataBase();
        }
        return instance;
    }

    private void establishConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        cnx = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Connection getCnx() {
        try {
            if (cnx == null || cnx.isClosed()) {
                System.out.println("Reconnecting...");
                establishConnection();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Reconnection failed: " + e.getMessage());
        }
        return cnx;
    }
}