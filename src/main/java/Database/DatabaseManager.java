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
    private static String baseDir;
    private static String dbPath;
    private static String url;

    /**
     * Simple constructor to connect a class to the database file
     */
    public DatabaseManager() {
        // Determine the base directory based on OS and user environment variables
        String appData = System.getenv("APPDATA");

        if (appData != null) {
            baseDir = appData + "\\EntroPass";
        } else {
            baseDir = System.getProperty("user.home") + "/EntroPass";
        }
        new File(baseDir).mkdirs(); // Ensure the directory exists

        dbPath = baseDir + File.separator + "PasswordDatabase.sqlite";
        url = "jdbc:sqlite:" + dbPath;
        try {
            File dbFile = new File(dbPath);
            if (!dbFile.getParentFile().mkdirs() && !dbFile.getParentFile().exists()) {
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

    public void initDatabase() {
        String createMasterKeyDBQuery = """
            CREATE TABLE IF NOT EXISTS master (
                            id INTEGER PRIMARY KEY CHECK (id = 1),
                            hash TEXT NOT NULL,
                            salt TEXT NOT NULL)
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
