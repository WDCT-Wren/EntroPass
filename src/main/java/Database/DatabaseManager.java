package Database;

import java.io.File;
import java.sql.*;

/**
 * This class manages the connection between the application and the database.
 * This is essentially the "JDBC" class where:
 * <ul>
 *     <li>The program establishes a connection with the database</li>
 *     <li>Other classes use this to make queries in the database (such as <b>insertion, deletion, and selection</b>)</li>
 * </ul>
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;
    private static final String DB_PATH = "src/main/resources/org/data/PasswordDataBase.sqlite"; //follow this path structure when making your own database

    /**
     *
     */
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
