package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public List<User> loadRepoData() {
        List<User> dataVault = new ArrayList<>();

        String query = "SELECT service_name, username, encrypted_password, notes, created_date FROM passwordDB";

        Connection connection = DatabaseManager.getInstance().getConnection();
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query)){

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

    public int getRowCount() {
        String query = "SELECT COUNT(*) FROM passwordDB";
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
