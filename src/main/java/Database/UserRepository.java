package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.*;

public class UserRepository {
    private String serviceName;
    private String userName;
    private String password;
    private String notes;
    private LocalDate createdDate;

    public UserRepository(String serviceName, String userName, String password, String notes) {
        this.serviceName = serviceName;
        this.userName = userName;
        this.password = password;
        this.notes = notes;
        this.createdDate = LocalDate.now();
    }

    public void insertPassword() throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();

        String insertSQL = "INSERT INTO passwords(service_name, username, encrypted_password, notes, created_date) VALUES(?,?,?,?,?)";
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

    public LocalDate getCreatedDate() {
        return createdDate;
    }
}
