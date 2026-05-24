package GUI.Controllers;

import java.io.IOException;
import java.util.Arrays;

import Database.MasterDAO;
import Encryption.AES;
import Encryption.PDKF2;
import GUI.Utils.SceneUtils;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;

public class AuthenticatorController {

    @FXML
    private PasswordField authTextField;

    @FXML
    private TextField viewAuthField;

    @FXML
    private Label validatorLabel;

    private static final String password = MasterDAO.retrieveMasterPass();
    boolean isViewable;

    @FXML
    private void initialize() {
        isViewable = false;
        validatorLabel.setVisible(false);

        authTextField.setOnMouseClicked(event -> {
            validatorLabel.setVisible(false);
        });

        authTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    switchToMenuScene(event);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //Initializes listener to sync textfield and password field of password.
        ChangeListener<String> passwordSync = SceneUtils.synchronizePasswordFields(authTextField, viewAuthField);
        authTextField.textProperty().addListener(passwordSync);
        authTextField.textProperty().addListener(passwordSync);
    }

    @FXML
    private void switchToForgetPasswordScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneUtils.getScene(stage, "ForgotPassword.fxml");
    }

    @FXML
    private void switchToSignUpScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneUtils.getScene(stage, "SignUpScene.fxml");
    }

    /**
     * Switches to the starting menu with the use of a button
     * @param event when the user presses "Unlock Vault"
     */
    @FXML
    private void switchToMenuScene(ActionEvent event) throws Exception {
        switchToMenuScene((Node) event.getSource());
    }

    /**
     * Switches to the starting menu with the use of the enter key
     * @param event when the user presses enter
     */
    @FXML
    private void switchToMenuScene(KeyEvent event) throws Exception {
        switchToMenuScene((Node) event.getSource());
    }

    /**
     * Validates the master password and switches to the main menu scene if successful.
     * Derives the AES key from the validated password and stores it for session use.
     *
     * @param source the UI node that triggered the scene switch, used to retrieve the current Stage
     * @throws Exception if key derivation, salt retrieval, or scene loading fails
     */
    private void switchToMenuScene(Node source) throws Exception {
        boolean validLogin = BCrypt.checkpw(authTextField.getText(), password);
        char[] masterPassword = authTextField.getText().toCharArray();

        if (validLogin) {
            SecretKey key = PDKF2.deriveKey(masterPassword, MasterDAO.retrieveSaltByte());

            Stage stage = (Stage) source.getScene().getWindow();
            SceneUtils.getScene(stage, "StartingMenu.fxml");

            AES.setKey(key); //inserts the key to the class.
        }
        else {
            validatorLabel.setVisible(true);
        }
    }

    @FXML
    void toggleView() {
        isViewable = !isViewable;
        viewPassword();
    }

    private void viewPassword() {
        authTextField.setVisible(!isViewable);
        viewAuthField.setVisible(isViewable);
    }
}
