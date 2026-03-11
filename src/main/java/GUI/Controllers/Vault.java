package GUI.Controllers;

import Database.*;
import GUI.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Vault implements Initializable {
    @FXML
    private Button BackButton;

    @FXML
    private Button copyPassword;

    @FXML
    private Button copyUsername;

    @FXML
    private Label createdDate;

    @FXML
    private TextField notes;

    @FXML
    private TextField password;

    @FXML
    private TextField serviceName;

    @FXML
    private Label serviceName1;

    @FXML
    private TextField userName;

    @FXML
    private ListView<String> userRepoList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            userRepoList.setItems(loadRepoData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ObservableList<String> loadRepoData() throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();

        ObservableList<String> dataVault = FXCollections.observableArrayList();
        String query = "SELECT service_name, username FROM passwordDB";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String serviceName = rs.getString("service_name");
                String username = rs.getString("username");
                dataVault.add(serviceName + "/" + username);
            }
        return dataVault;
    }

    @FXML
    public void switchToMenuScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/org/password_generator_gui/Scenes/StartingMenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void copyPassword() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        //TODO: create copy logic for password
    }

    @FXML
    private void copyUsername() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        //TODO: create copy logic for Username
    }
}