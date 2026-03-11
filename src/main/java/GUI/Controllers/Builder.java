package GUI.Controllers;

import Database.UserOperator;
import Database.PasswordHasher;
import GUI.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.password_generator.Configurator;
import org.password_generator.StrengthChecker;

// Source - https://stackoverflow.com/a/74555584
// Posted by Mustafa Poya
// Retrieved 2026-03-06, License - CC BY-SA 4.0

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.IOException;
import java.sql.SQLException;

public class Builder {
    @FXML
    private Button copyButton;
    @FXML
    private ProgressBar strengthIndicator;
    @FXML
    private Slider passwordLengthSlider;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField serviceNameField;
    @FXML
    private TextField noteField;
    @FXML
    private Label passwordText;
    @FXML
    private TextField passLength;
    @FXML
    private CheckBox mixedCaseCheckBox;
    @FXML
    private CheckBox specialCharCheckBox;
    @FXML
    private CheckBox numberCheckBox;
    @FXML
    private Label passwordStrength;

    @FXML
    private void initialize() {
        if (passLength != null) {
            passLength.setText("8");

            //prevents users from entering anything other than numbers.
            passLength.textProperty().addListener((_, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) { //if the new value does not match any possible digit through regex checking
                    passLength.setText(oldValue); //the passLength would be set back to the old value
                }
            });
        }

        passwordLengthSlider.valueProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                passLength.setText(String.valueOf(newValue.intValue()));
            }
        });

        strengthIndicator.setStyle("-fx-accent: #64ED86;");
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
    private void generatePassword() {
        Configurator configuration = new Configurator(numberCheckBox.isSelected(), specialCharCheckBox.isSelected(), mixedCaseCheckBox.isSelected());
        try {
            if (getPasswordLength() < 8 || getPasswordLength() > 128) passwordText.setText("Length must be between 8 and 128 characters!");

            int passwordLength = getPasswordLength();
            setPassword(passwordLength, configuration);
            setPasswordStrength(configuration, passwordLength);
            copyButton.setText("copy to clipboard");
        }
        catch (NumberFormatException e) {
            passwordText.setText("Please enter a valid number");
        }
    }

    @FXML
    private void setPassword(int passwordLength, Configurator configuration) {
        org.password_generator.Builder builder = new org.password_generator.Builder(passwordLength);
        String password = builder.buildPassword(configuration);
        passwordText.setText(password);

        if (password.length() > 28) {
            double baseSize = 16.0;
            double newSize = Math.max(7.0, baseSize * (28.0 / password.length()));
            passwordText.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-font-weight: bold; -fx-font-size: " + String.format("%.1f", newSize) + ";");
        } else {
            passwordText.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-font-weight: bold; -fx-font-size: 16.0;");
        }
    }

    @FXML
    private void setPasswordStrength(Configurator configuration, int passwordLength) {
        double strength = StrengthChecker.getStrength(configuration, passwordLength);

        passwordStrength.setText(
                StrengthChecker.
                checkStrength(strength)
        );

        strengthIndicator.setProgress(strength);
    }

    @FXML
    private void savePassword() throws SQLException {
        UserOperator newRepo = new UserOperator(getServiceName(), getUsername(), getHashedPassword(), getNote());
        newRepo.insertPassword();

        passwordText.setText("Password Saved Successfully!");
        passwordText.setFont(Font.font(16));
    }

    @FXML
    private void copyToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(passwordText.getText());
        clipboard.setContent(content);

        copyButton.setText("Copied to Clipboard!");
    }


    //Getter Methods
    private int getPasswordLength() {return Integer.parseInt(passLength.getText());}
    private String getUsername() {
        if (usernameField == null) {
            return "";
        }
        return usernameField.getText();
    }
    private String getServiceName() {
        if (serviceNameField == null) {
            return "";
        }
        return serviceNameField.getText();
    }
    private String getNote() {
        if (noteField == null) {
            return "";
        }
        return noteField.getText();
    }
    private String getHashedPassword() {return PasswordHasher.hashPassword(passwordText.getText());}
}
