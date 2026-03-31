package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * User DAO(Data Access Object) which is used to retrieve data from the database <b>PasswordDB</b>.
 * <ul>retrieving data such as:
 *      <li>User repository data from the {@link User}</li>
 *      <li>The number of rows in the database</li>
 * </ul>
 */
public class UserDAO {
    /**
     * Method used to access the database and retrieve necessary entries
     * in the password creation process that was saved.
     *
     * @return <ul> A list of the following data:
     *          <li>The password's service name (e.g., Spotify, YouTube, Netflix)</li>
     *          <li>The username</li>
     *          <li>The password (encrypted and is about to be decrypted)</li>
     *          <li>Optional notes (e.g., answers for security questions)</li>
     *          <li>The creation date</li>
     *      </ul>
     */
    public List<User> loadRepoData() {
        List<User> dataVault = new ArrayList<>();

        //Prepares the query
        String query = "SELECT service_name, username, encrypted_password, notes, created_date FROM vault";

        //Establish a connection with the database
        Connection connection = DatabaseManager.getInstance().getConnection();
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query)){

            //Loops through the rows, adding a user object to the dataVault list for every table row.
            while (rs.next()) {
                dataVault.add(new User(
                        rs.getString("service_name"),
                        rs.getString("username"),
                        rs.getString("encrypted_password"),
                        rs.getString("notes"),
                        rs.getString("created_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataVault;
    }

    /**
     * Method that accesses the database and counts the number of existing entries/rows
     * @return a count of the row entries in the database
     */
    public int getRowCount() {
        String query = "SELECT COUNT(*) FROM vault";
        Connection connection = DatabaseManager.getInstance().getConnection();
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query)){

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
