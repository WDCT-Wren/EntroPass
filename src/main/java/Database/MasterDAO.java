package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class MasterDAO {
    public static String retrieveMasterPass() {
        String masterPassword = "";

        //Establish a connection with the database
        Connection connection = DatabaseManager.getInstance().getConnection();

        //Prepares the query
        String query = "SELECT hash FROM master WHERE id = 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            masterPassword = rs.getString("hash");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return masterPassword;
    }

    public static byte[] retrieveSaltByte() {
        byte[] salt = new byte[16];

        //Establish a connection with the database
        Connection connection = DatabaseManager.getInstance().getConnection();

        //prepare the query
        String query = "SELECT salt from master WHERE id = 1";

        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query)){

            salt = rs.getBytes("salt");
            System.out.println(Arrays.toString(salt));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salt;
    }
}
