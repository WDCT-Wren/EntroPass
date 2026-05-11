package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperations {
    /**
     * Method that inserts the important user credentials that will be used in vault decryption too.
     * @param masterPassword the master password that grants the user access to the application in a hash.
     * @param salt key part in the PDKF2 derivation of the AES decryption key
     * @throws SQLException if the query did not go through
     */
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

    /**
     * Clears the database after the user confirms that they've completely forgotten
     * their master password. <br><br>
     * no master password -> forget password? -> delete the databases
     */
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
