package GUI.Controllers;

import Database.*;
import Encryption.PasswordEncrypt;
import GUI.Application;
import GUI.views.VaultEntryCell;

import java.io.IOException;
import java.sql.SQLException;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class VaultController implements Initializable {
    @FXML
    private Label itemAmountLabel;

    @FXML
    private Button BackButton;

    @FXML
    private Button copyPassword;

    @FXML
    private Button copyUsername;

    @FXML
    private ImageView notesIcon;

    @FXML
    private ImageView copyIcon;

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
    private ListView<User> userRepoList;

    private final UserDAO userDAO = new UserDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userRepoList.setCellFactory(lv -> new VaultEntryCell());
        userRepoList.getItems().addAll(userDAO.loadRepoData());

        userRepoList.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        populateDetail(newValue);
                    }
                })
        );

        userRepoList.getSelectionModel().selectFirst();
        notesIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("/org/password_generator_gui/Icons/Images/noteIcon.png")).toExternalForm()));

        itemAmountLabel.setText(String.valueOf(userDAO.getRowCount()));
    }

    private void populateDetail(User user) {
        serviceName.setText(user.getServiceName());
        userName.setText(user.getUserName());
        password.setText(PasswordEncrypt.decrypt(user.getPassword()));
        notes.setText(user.getNotes());
        createdDate.setText(user.getCreatedDate());
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
    private void copyPassword() throws InterruptedException {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(password.getText());
        clipboard.setContent(content);

        copyPassword.setText("copied to clipboard!");
        copyUsername.setText("copy to clipboard");
    }

    @FXML
    private void copyUsername() throws InterruptedException {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(userName.getText());
        clipboard.setContent(content);

        copyUsername.setText("copied to clipboard!");
        copyPassword.setText("copy to clipboard");
    }
}