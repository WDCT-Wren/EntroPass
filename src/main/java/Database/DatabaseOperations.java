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

    public static void purgeDB() throws SQLException {
        //Establish a connection first
        Connection connection = DatabaseManager.getInstance().getConnection();

        //Declare the vault table and the master table queries
        String purgeVault = "delete from vault";
        String purgeMaster = "delete from master";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(purgeVault);
            stmt.execute(purgeMaster);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePassword(int id) {
        // Establish a connection first
        Connection connection = DatabaseManager.getInstance().getConnection();

        // Declare the delete query
        String sql = "DELETE FROM vault WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
