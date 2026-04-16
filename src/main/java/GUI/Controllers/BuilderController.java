package GUI.Controllers;

import Database.UserOperations;
import Encryption.AES;
import GUI.Utils.SceneUtils;
import GUI.Utils.StrengthUIHelper;
import com.sun.javafx.scene.control.FormatterAccessor;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.Password_Generator.Configurator;
import org.Password_Generator.StrengthChecker;


import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.IOException;
import java.sql.SQLException;

public class BuilderController {
    @FXML
    private HBox manualEntryButton;
    @FXML
    private Button copyButton;
    @FXML
    private Button passwordGenerate;
    @FXML
    private ProgressBar strengthIndicator;
    @FXML
    private Slider passwordLengthSlider;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField serviceNameField;
    @FXML
    private TextArea noteField;
    @FXML
    private TextField passwordText;
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

    private boolean isManualEntry = false;
    private ChangeListener<String> manualStrengthListener;

    /**
     *
     */
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
    }

    /**
     * Method that handles the scene switch from the current password generator scene
     * to the main menu scene.
     *
     * @param event the event of pressing the {@code back button}
     * @throws IOException an unexpected event in the input
     */
    @FXML
    public void switchToMenuScene(ActionEvent event) throws IOException {
        String fxmlFileName = "StartingMenu.fxml";
        String cssFileName = "StartingMenuStyleSheet.css";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneUtils.getScene(stage, fxmlFileName, cssFileName);
    }

    /**
     * This method handles the action of pressing the {@code Generate Password} button./
     */
    @FXML
    private void generatePassword() {
        Configurator configuration = new Configurator(numberCheckBox.isSelected(), specialCharCheckBox.isSelected(), mixedCaseCheckBox.isSelected());
        passwordGenerate.setText("Regenerate");
        try {
            if (getPasswordLength() < 8 || getPasswordLength() > 64)
                passwordText.setText("Length must be between 8 and 64 characters!");

            int passwordLength = getPasswordLength();
            setPassword(passwordLength, configuration);
            setPasswordStrength(configuration, passwordLength);
            copyButton.setText("copy to clipboard");
        } catch (NumberFormatException e) {
            passwordText.setText("Please enter a valid number");
        }
    }

    @FXML
    private void setPassword(int passwordLength, Configurator configuration) {
        org.Password_Generator.Builder builder = new org.Password_Generator.Builder(passwordLength);
        String password = builder.buildPassword(configuration);
        passwordText.setText(password);

        double maxLength = 40;
        double baseSize = 18.0;
        if (password.length() > maxLength) {
            double minSize = 7.0;
            double newSize = Math.max(minSize, baseSize * (maxLength / password.length()));
            passwordText.setStyle("-fx-font-size: " + String.format("%.1f", newSize) + ";");
        } else {
            passwordText.setStyle("-fx-font-size: baseSize;");
        }
    }

    @FXML
    private void setPasswordStrength(Configurator configuration, int passwordLength) {
        double entropyBits = StrengthChecker.getGeneratorEntropy(configuration, passwordLength);
        double normalizedStrength = StrengthChecker.getNormalizedValue(entropyBits);


        passwordStrength.setText(
                StrengthChecker.
                        checkGeneratedStrength(entropyBits)
        );

        strengthIndicator.setProgress(normalizedStrength);
    }

    @FXML
    private void savePassword() throws SQLException {
        UserOperations newRepo = new UserOperations(getServiceName(), getUsername(), getEncryptedPassword(), getNote());
        boolean invalidServiceName = serviceNameField.getText().isEmpty();
        boolean invalidUserName = usernameField.getText().isEmpty();

        if (invalidServiceName) {
            serviceNameField.setPromptText("This field is required!");
        }
        if (invalidUserName) {
            usernameField.setPromptText("This field is required!");
        }
        if (!invalidUserName && !invalidServiceName) {
            newRepo.insertPassword();
            passwordText.setStyle("-fx-font-size: 18");
            passwordText.setText("Password Saved Successfully!");
        }
    }

    @FXML
    private void copyToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(passwordText.getText());
        clipboard.setContent(content);

        copyButton.setText("Copied to Clipboard!");
    }

    @FXML
    public void toggleManualEntry() {
        isManualEntry = !isManualEntry;
        passwordText.setEditable(isManualEntry);
        passwordGenerate.setDisable(isManualEntry);

        if (isManualEntry) {
            strengthIndicator.setProgress(0);
            passwordText.setStyle("-fx-font-size: 20; -fx-text-fill: #a3e635");
            passwordText.setText("");
            passwordText.setPromptText("Input YOUR OWN banger password here!");
            manualStrengthListener = StrengthUIHelper.manualStrengthListener(strengthIndicator);
            passwordText.textProperty().addListener(manualStrengthListener);
        } else {
            nullifyListener();
            strengthIndicator.setProgress(0);
            passwordText.setStyle("-fx-font-size: 25; -fx-text-fill: #a3e635");
            passwordText.setText("");
            passwordText.setPromptText("Banger generated password here");
        }
    }

    @FXML
    public void switchToVaultScene(ActionEvent event) throws IOException {
        String fxmlFile = "PasswordVault.fxml";
        String cssFile = "VaultStyleSheet.css";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SceneUtils.getScene(stage, fxmlFile, cssFile);
    }

    /**
     * Nullifies the listener if it exists.
     */
    private void nullifyListener() {
        if (manualStrengthListener != null) {
            passwordText.textProperty().removeListener(manualStrengthListener);
            manualStrengthListener = null;
        }
    }

    //Getter Methods
    private int getPasswordLength() {
        return Integer.parseInt(passLength.getText());
    }
    private String getUsername() {
        return usernameField.getText();
    }
    private String getServiceName() {
        return serviceNameField.getText();
    }
    private String getNote() {
        if (noteField.getText().isEmpty()) return "";
        return noteField.getText();
    }
    private String getEncryptedPassword() {
        return AES.encrypt(passwordText.getText());
    }
}
