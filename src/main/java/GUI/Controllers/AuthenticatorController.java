package GUI.Controllers;

import java.io.IOException;
import java.util.Arrays;

import Database.MasterDAO;
import Encryption.AES;
import Encryption.PDKF2;
import GUI.Utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;

public class AuthenticatorController {

    @FXML
    private PasswordField AuthTextField;

    @FXML
    private Label validatorLabel;

    private static final String password = MasterDAO.retrieveMasterPass();

    public AuthenticatorController() throws Exception {
    }

    @FXML
    private void initialize() {
        validatorLabel.setVisible(false);

        AuthTextField.setOnMouseClicked(event -> {
            validatorLabel.setVisible(false);
        });

        AuthTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    switchToMenuScene(event);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    private void switchToForgetPasswordScene(ActionEvent event) throws IOException {
        String fxmlFileName = "ForgotPassword.fxml";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneUtils.getScene(stage, fxmlFileName);
    }

    @FXML
    private void switchToSignUpScene(ActionEvent event) throws IOException {
        String fxmlFileName = "SignUpScene.fxml";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneUtils.getScene(stage, fxmlFileName);
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
        boolean validLogin = BCrypt.checkpw(AuthTextField.getText(), password);
        char[] masterPassword = AuthTextField.getText().toCharArray();

        if (validLogin) {
            SecretKey key = PDKF2.deriveKey(masterPassword, MasterDAO.retrieveSaltByte());
            String fxmlFileName = "StartingMenu.fxml";

            Stage stage = (Stage) source.getScene().getWindow();
            SceneUtils.getScene(stage, fxmlFileName);

            AES.setKey(key); //inserts the key to the class.
        }
        else {
            validatorLabel.setVisible(true);
        }
    }
}
