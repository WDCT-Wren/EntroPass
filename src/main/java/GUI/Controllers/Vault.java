package GUI.Controllers;

import Database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Vault implements Initializable {
    @FXML
    private TableColumn<User, String> createdDate;

    @FXML
    private TableColumn<User, String> notes;

    @FXML
    private TableColumn<User, String> password;

    @FXML
    private TableColumn<User, String> serviceName;

    @FXML
    private TableColumn<User, String> userName;

    @FXML
    private TableView<User> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        createdDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

        try {
            ObservableList<User> repositoryList = loadRepoData();
            table.setItems(repositoryList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public ObservableList<User> loadRepoData() throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();

        ObservableList<User> dataVault = FXCollections.observableArrayList();
        String query = "SELECT service_name, username, encrypted_password, notes, created_date FROM passwordDB";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                dataVault.add(new User(
                        rs.getString("service_name"),
                        rs.getString("username"),
                        rs.getString("encrypted_password"),
                        rs.getString("notes"),
                        rs.getString("created_date")
                ));
            }
        return dataVault;
    }
}