package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    private static MyDataBase instance;
    private Connection cnx;

    private static final String URL = "jdbc:mysql://localhost:3306/event planner?serverTimezone=UTC";
    private static final String USER = "root"; // Remplacez par votre nom d'utilisateur MySQL
    private static final String PASSWORD = ""; // Remplacez par votre mot de passe MySQL

    private MyDataBase() {
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données réussie !");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    public static MyDataBase getInstance() {
        if (instance == null) {
            instance = new MyDataBase();
        }
        return instance;
    }

    public Connection getConnection() {
        return cnx;
    }
}