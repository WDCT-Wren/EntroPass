package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperations {
    public static void insertToMasterDB(String masterPassword, String salt) throws SQLException {
        //Establishes a connection first
        Connection connection = DatabaseManager.getInstance().getConnection();

        //Prepare SQL query with placeholders
        String insertSQL = "INSERT INTO master(hash, salt) VALUES(?, ?)";

        //Prepares the query execution with the necessary parameters.
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, masterPassword);
        preparedStatement.setString(2, salt);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void purgeDB() {
        //Establish a connection first
        Connection connection = DatabaseManager.getInstance().getConnection();

        //Declare the vault table and the master table queries
        String purgeVault = "DELETE FROM vault";
        String purgeMaster = "DELETE FROM master";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(purgeVault);
            stmt.execute(purgeMaster);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
