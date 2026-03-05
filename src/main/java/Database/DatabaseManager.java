package Database;

import java.io.File;
import java.sql.*;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;
    private static final String DB_PATH = "src/main/resources/data/PasswordDataBase.sqlite";

    public  DatabaseManager() {
        try {
            File dbFile = new File(DB_PATH);
            if (!dbFile.exists()) {
                System.out.println("Error: Database file not found at " + DB_PATH);
                return;
            }
            String url = "jdbc:sqlite:" + DB_PATH;
            this.connection = DriverManager.getConnection(url);
            System.out.println("Opened database connection!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {return connection;}

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
