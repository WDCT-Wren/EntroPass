package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

/**
 * Class used to retrieve data from the database {@code MasterDB}.
 */
public class MasterDAO {
    /**
     * Retrieves the master password from the database.
     * @return the master password as a hashed string
     */
    public static String retrieveMasterPass() {
        //Establish a connection with the database
        Connection connection = DatabaseManager.getInstance().getConnection();

        //Prepares the query
        String query = "SELECT hash FROM master WHERE id = 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            if (rs.next()) {
                return rs.getString("hash");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves the salt from the database.
     * @return the salt as a byte array
     */
    public static byte[] retrieveSaltByte() {
        byte[] salt = new byte[16];

        //Establish a connection with the database
        Connection connection = DatabaseManager.getInstance().getConnection();

        //prepare the query
        String query = "SELECT salt from master WHERE id = 1";

        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query)){

            salt = rs.getBytes("salt");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salt;
    }
}
