package Database.UserCRUD;

import Database.DatabaseManager;

import java.sql.*;
import java.time.*;

/**
 * Class that is used to execute database queries such as <b>Data Insertion and Deletion.</b>
 * <br><br>
 * This class's function is different from {@link UserDAO}, since this class is used to manage
 * data from the database such as insert new data or delete new data, whereas the {@link UserDAO}
 * is specifically used to retrieve data only.
 */
public class UserOperations {
    private final String serviceName;
    private final String userName;
    private final String password;
    private final String notes;
    private final String createdDate;

    /**
     * Object constructor used to make an entry, or delete an entry to the database.
     * @param serviceName the password's service (e.g., Netflix, Spotify, etc.)
     * @param userName the entry's username
     * @param password the encrypted password
     * @param notes Optional (e.g., answers for security questions)
     */
    public UserOperations(String serviceName, String userName, String password, String notes) {
        this.serviceName = serviceName;
        this.userName = userName;
        this.password = password;
        this.notes = notes;
        this.createdDate = LocalDate.now().toString(); // LocalDate library is used to store the date when the password is saved.
    }

    /**
     * Method used to make an insertion query that inputs the password credentials to the database.
     */
    public void insertPassword() throws SQLException {
        //Establishes a connection first
        Connection connection = DatabaseManager.getInstance().getConnection();

        //Declare the database query with placeholders
        String insertSQL = "INSERT INTO vault(service_name, username, encrypted_password, notes, created_date) " +
                "VALUES(?,?,?,?,?)";

        //Prepares the query execution with the necessary parameters.
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, serviceName);
        preparedStatement.setString(2, userName);
        preparedStatement.setString(3, password);
        preparedStatement.setString(4, notes);
        preparedStatement.setObject(5, createdDate);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Deletes a certain entry of user credentials in the database.
     * @param id the id of the entry to be deleted as an integer
     */
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

    /**
     * Updates a certain entry in the database based on the id passed
     * which also updates the created_date column, essentially making it
     * {@code Last Updated}
     */
    public void updatePassword(int id) {
        // Estabblish a connection first
        Connection connection = DatabaseManager.getInstance().getConnection();

        // Declare the update query
        String sql = "UPDATE vault SET service_name = ?,  username = ?, encrypted_password = ?, notes = ?, created_date = ?  WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, serviceName);
            stmt.setString(2, userName);
            stmt.setString(3, password);
            stmt.setString(4, notes);
            stmt.setObject(5, createdDate);
            stmt.setInt(6, id);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
