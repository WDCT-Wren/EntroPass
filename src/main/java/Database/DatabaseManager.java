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

    //follow this path structure when making your own database
    private static final String DB_PATH = "src/main/resources/org/data/PasswordDataBase.sqlite";
    private static final String url = "jdbc:sqlite:" + DB_PATH;
    /**
     * Simple constructor to connect a class to the database file
     */
    public  DatabaseManager() {
        try {
            File dbFile = new File(DB_PATH);
            if (!dbFile.exists()) {
                System.out.println("Database Connection cannot be found!");
            }
            this.connection = DriverManager.getConnection(url);
            System.out.println("Opened database connection!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @return an instance of the connection
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void initDatabase() throws SQLException {
        connection = DriverManager.getConnection(url);
        String createMasterKeyDBQuery = """
                CREATE TABLE IF NOT EXISTS master (
                                hash TEXT NOT NULL)
                """;
        String createVaultQuery = """
                create table if not exists vault (
                    id                 INTEGER not null
                        constraint vault_pk
                            primary key autoincrement,
                    service_name       TEXT    not null,
                    username           TEXT    not null,
                    encrypted_password TEXT    not null,
                    notes              TEXT,
                    created_date       TEXT
                )
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createMasterKeyDBQuery);
            stmt.execute(createVaultQuery);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return connects the instance of the connector to the database
     */
    public Connection getConnection() {return connection;}

    /**
     * Closes the connection of the application to the database with an additional log of the database connection closure.
     * Shall only be used when closing the application to prevent premature database connection closure
     */
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
