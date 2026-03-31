package Database;

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
        String insertSQL = "INSERT INTO vault(service_name, username, encrypted_password, notes, created_date) VALUES(?,?,?,?,?)";

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

    //TODO: Code removePassword() method.
}
