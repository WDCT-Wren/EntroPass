package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.*;

public class UserOperator {
    private final String serviceName;
    private final String userName;
    private final String password;
    private final String notes;
    private final String createdDate;

    public UserOperator(String serviceName, String userName, String password, String notes) {
        this.serviceName = serviceName;
        this.userName = userName;
        this.password = password;
        this.notes = notes;
        this.createdDate = LocalDate.now().toString();
    }

    public void insertPassword() throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();

        String insertSQL = "INSERT INTO passwordDB(service_name, username, encrypted_password, notes, created_date) VALUES(?,?,?,?,?)";
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
