package GUI.Controllers;

import Database.DatabaseOperations;
import Database.MasterDAO;
import Encryption.PBKDF2;
import GUI.Utils.SceneUtils;
import GUI.Utils.StrengthUIHelper;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {

    @FXML
    private VBox root;

    @FXML
    private Button setKey;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ProgressBar masterKeyStrength;

    @FXML
    private PasswordField masterPasswordField;

    @FXML
    private TextField masterPasswordView;

    @FXML
    private TextField confirmPasswordView;

    @FXML
    private Label passwordStrength;

    @FXML
    private Label inputWarning;

    boolean isMasterKeyViewable;
    boolean isConfirmKeyViewable;

    @FXML
    public void initialize() {
        isMasterKeyViewable = false;
        isConfirmKeyViewable =false;

        // Initialized visible objects
        masterPasswordView.setVisible(false);
        confirmPasswordView.setVisible(false);
        inputWarning.setVisible(false);
        inputWarning.setManaged(false);

        masterPasswordField.setOnMouseClicked(event -> {
            inputWarning.setVisible(false);
            inputWarning.setManaged(false);
        });

        // Initializes listener to sync both fields in master password setup
        ChangeListener<String> passwordSync = SceneUtils.synchronizePasswordFields(masterPasswordField, masterPasswordView);
        masterPasswordField.textProperty().addListener(passwordSync);
        masterPasswordView.textProperty().addListener(passwordSync);

        // Initializes listener to sync both fields in password confirmation
        ChangeListener<String> confirmationSync = SceneUtils.synchronizePasswordFields(confirmPasswordField, confirmPasswordView);
        confirmPasswordField.textProperty().addListener(confirmationSync);
        confirmPasswordView.textProperty().addListener(confirmationSync);

        // Initializes evaluator for manually entered passwords.
        masterPasswordField.textProperty().addListener(
                StrengthUIHelper.manualStrengthListener(masterKeyStrength, passwordStrength));

        // Define a single listener to hide the warning
        ChangeListener<String> clearWarning = (observable, oldValue, newValue) -> {
            inputWarning.setVisible(false);
            inputWarning.setManaged(false);
        };
        masterPasswordField.textProperty().addListener(clearWarning);
        confirmPasswordField.textProperty().addListener(clearWarning);
    }

    /**
     * Method that primarily processes the insertion of a new hashed master key
     * with its corresponding pbkdf2 salt.
     */
    private void insertIntoDB() {
        // Fail-fast validation (No DB calls if field is empty)
        if (getMasterPass().isEmpty()) {
            inputWarning.setText("Master Key cannot be empty!");
            inputWarning.setVisible(true);
            inputWarning.setManaged(true);
            return;
        }

        try {
            // Check if account already exists before trying to insert
            if (MasterDAO.retrieveMasterPass() != null) {
                inputWarning.setText("You already have an account! Sign in instead!");
                inputWarning.setVisible(true);
                inputWarning.setManaged(true);
                return;
            }

            // Perform database operations safely inside the try block
            String salt = PBKDF2.getSalt();
            DatabaseOperations.insertToMasterDB(getHashedMasterPass(), salt);

            // UI changes after successful SQLite setup
            setKey.setDisable(true);
            setKey.setText("Vault Created! Redirecting...");
            setKey.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white");

            // Let them process the success state, then auto-route to login
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> {
                // load the Sign-In page
                switchToSignInScene();
            });
            delay.play();

        } catch (SQLException e) {
            e.printStackTrace();
            inputWarning.setText("Database error occurred. Please try again.");
        }
    }

    /**
     * Switches the scene from the sign-up to the sign-in where the user can now enter their newly
     * stored master key and access their new vault
     *
     * @throws IOException if the event didn't go through
     */
    @FXML
    private void switchToSignInScene() {
        try {
            // Pull the stage from an existing FXML element instead of the event source
            Stage stage = (Stage) setKey.getScene().getWindow();

            boolean masterPasswordExists = MasterDAO.retrieveMasterPass() != null;

            if (masterPasswordExists) {
                SceneUtils.getScene(stage, "AuthMenu.fxml");
            } else {
                inputWarning.setText("You don't have a stored account yet! Make one first before you can sign in!");
                inputWarning.setVisible(true);
                inputWarning.setManaged(true);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Or handle your scene loading error gracefully
        }
    }

    @FXML
    void toggleMasterPassView() {
        isMasterKeyViewable = !isMasterKeyViewable;
        viewMasterPassword();
    }

    private void viewMasterPassword() {
        masterPasswordField.setVisible(!isMasterKeyViewable);
        masterPasswordView.setVisible(isMasterKeyViewable);
    }

    @FXML
    void toggleConfirmPassView() {
        isConfirmKeyViewable = !isConfirmKeyViewable;
        viewConfirmPassword();
    }

    private void viewConfirmPassword() {
        confirmPasswordField.setVisible(!isConfirmKeyViewable);
        confirmPasswordView.setVisible(isConfirmKeyViewable);
    }

    private boolean passwordsMatch() {
        String passwordConfirmationText = confirmPasswordField.getText();
        return getMasterPass().equals(passwordConfirmationText);
    }

    // helper getter methods
    private String getHashedMasterPass() { return BCrypt.hashpw(masterPasswordField.getText(), BCrypt.gensalt());}
    private String getMasterPass() { return masterPasswordField.getText();}

    public void setMasterKey() throws IOException {
        if (passwordsMatch()) {
            // Update UI upon successful database insert
            insertIntoDB();
        }
        else {
            inputWarning.setText("New password doesn't match!");
            inputWarning.setVisible(true);
            inputWarning.setManaged(true);
        }
    }
}
