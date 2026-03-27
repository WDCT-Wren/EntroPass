package GUI.Controllers;

import java.io.IOException;

import Encryption.AES;
import Encryption.PDKF2;
import Encryption.ValidatePassword;
import GUI.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.crypto.SecretKey;

public class AuthenticatorController {

    @FXML
    private PasswordField AuthTextField;

    @FXML
    private Label validatorLabel;

    @FXML
    private Button UnlockButton;

    private static final String HASHED_PASS = "$2a$12$yjGunLLYocir1U6fpY6tPOtJflUFG..wWVaLofXXMlDU8.81/USXW";
    SecretKey key = PDKF2.deriveKey(HASHED_PASS.toCharArray());

    public AuthenticatorController() throws Exception {
    }

    @FXML
    void initialize() {
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
    private boolean validateLogIn() throws IOException {
        return ValidatePassword.checkPassword(AuthTextField.getText(), HASHED_PASS);
    }

    @FXML
    private void switchToMenuScene(ActionEvent event) throws Exception {
        if (validateLogIn()) {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/org/password_generator_gui/Scenes/StartingMenu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            AES.setKey(key); //inserts the key to the class.
        }
        else {
            validatorLabel.setVisible(true);
        }
    }

    @FXML
    private void switchToMenuScene(KeyEvent event) throws Exception {
        if (validateLogIn()) {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/org/password_generator_gui/Scenes/StartingMenu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            AES.setKey(key); //inserts the key to the class.
        }
        else {
            validatorLabel.setVisible(true);
        }
    }
}
