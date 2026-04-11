package GUI.Controllers;

import Database.*;
import Encryption.AES;
import GUI.Application;
import GUI.Utils.SceneUtils;
import GUI.views.VaultEntryCell;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class VaultController implements Initializable {
    @FXML
    private TextField searchField;

    @FXML
    private Label itemAmountLabel;

    @FXML
    private Button copyPassword;

    @FXML
    private Button copyUsername;

    @FXML
    private Label createdDate;

    @FXML
    private TextArea notes;

    @FXML
    private PasswordField password;

    @FXML
    private Label serviceName;

    @FXML
    private TextField userName;

    @FXML
    private ListView<User> userRepoList;
    private final UserDAO userDAO = new UserDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate the listview with all the assets initially
        populateList();

        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    if (searchField.getText().isEmpty()) {
                        populateList();
                    }
                    else {
                        populateSearchedList();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        userRepoList.getSelectionModel().selectFirst();

        itemAmountLabel.setText(String.valueOf(userDAO.getRowCount()));
    }

    private void populateList() {
        userRepoList.getItems().clear();
        userRepoList.setCellFactory(lv -> new VaultEntryCell());
        userRepoList.getItems().addAll(userDAO.loadRepoData());

        userRepoList.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        populateDetail(newValue);
                    }
                })
        );
    }

    private void populateSearchedList() throws SQLException {
        userRepoList.getItems().clear();
        String searched = searchField.getText();
        userRepoList.setCellFactory(lv -> new VaultEntryCell());
        userRepoList.getItems().addAll(userDAO.loadRepoData(searched));

        userRepoList.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        populateDetail(newValue);
                    }
                })
        );
    }

    private void populateDetail(User user) {
        serviceName.setText(user.getServiceName());
        userName.setText(user.getUserName());

        try {
            password.setText(AES.decrypt(user.getPassword()));
        } catch (RuntimeException e) {
            password.setText("[Legacy Password - Please re-enter password]");
            password.setStyle("-fx-text-fill: #FF6B6B;");
        }

        notes.setText(user.getNotes());
        createdDate.setText(user.getCreatedDate());
    }

    @FXML
    public void switchToMenuScene(ActionEvent event) throws IOException {
        String fxmlFile = "StartingMenu.fxml";
        String cssFile = "StartingMenuStyleSheet.css";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SceneUtils.switchScene(stage, fxmlFile, cssFile);
    }

    @FXML
    private void copyPassword() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(password.getText());
        clipboard.setContent(content);

        copyPassword.setText("Copied!");
        copyUsername.setText("Copy");
    }

    @FXML
    private void copyUsername() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(userName.getText());
        clipboard.setContent(content);

        copyUsername.setText("Copied!");
        copyPassword.setText("Copy");
    }

    @FXML
    public void deleteEntry() throws SQLException {
        User user = userRepoList.getSelectionModel().getSelectedItem();
        int id = user.getId();
        DatabaseOperations.deletePassword(id);

        populateDetail(user);
        populateList();
        itemAmountLabel.setText(String.valueOf(userDAO.getRowCount()));
        userRepoList.getSelectionModel().selectFirst();
    }
}