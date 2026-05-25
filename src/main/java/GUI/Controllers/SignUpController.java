package GUI.Controllers;

import Database.DatabaseOperations;
import Database.MasterDAO;
import Encryption.PDKF2;
import GUI.Utils.SceneUtils;
import GUI.Utils.StrengthUIHelper;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {

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
    private Button setKey;

    @FXML
    private Button signInButton;

    @FXML
    private Label signInWarning;

    @FXML
    private Button viewPassword;

    @FXML
    private Button viewPassword1;

    boolean isMasterKeyViewable;
    boolean isConfirmKeyViewable;

    @FXML
    public void initialize() {
        isMasterKeyViewable = false;
        isConfirmKeyViewable =false;

        // Initialized visible objects
        masterPasswordView.setVisible(false);
        confirmPasswordView.setVisible(false);
        signInWarning.setVisible(false);
        signInWarning.setManaged(false);

        masterPasswordField.setOnMouseClicked(event -> {
            signInWarning.setVisible(false);
            signInWarning.setManaged(false);
        });

        //Initializes listener to sync both fields in master password setup
        ChangeListener<String> passwordSync = SceneUtils.synchronizePasswordFields(masterPasswordField, masterPasswordView);
        masterPasswordField.textProperty().addListener(passwordSync);
        masterPasswordView.textProperty().addListener(passwordSync);

        //Initializes listener to sync both fields in password confirmation
        ChangeListener<String> confirmationSync = SceneUtils.synchronizePasswordFields(confirmPasswordField, confirmPasswordView);
        confirmPasswordField.textProperty().addListener(confirmationSync);
        confirmPasswordView.textProperty().addListener(confirmationSync);

        masterPasswordField.textProperty().addListener(
                StrengthUIHelper.manualStrengthListener(masterKeyStrength, passwordStrength));
    }

    @FXML
    void switchToSignInScene(ActionEvent event) throws IOException {
        boolean masterPasswordExists = MasterDAO.retrieveMasterPass() != null;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (masterPasswordExists) SceneUtils.getScene(stage, "AuthMenu.fxml");
        else {
            signInWarning.setText("You don't have a stored account yet! Make one first before you can sign in!");
            signInWarning.setVisible(true);
            signInWarning.setManaged(true);
        }
    }

    @FXML
    private void insertIntoDB(ActionEvent event) throws IOException {
        // Fail-fast validation (No DB calls if field is empty)
        if (getMasterPass().isEmpty()) {
            masterPasswordField.setPromptText("MASTER PASSWORD CANNOT BE EMPTY");
            return;
        }

        try {
            // Check if account already exists before trying to insert
            if (MasterDAO.retrieveMasterPass() != null) {
                signInWarning.setText("You already have an account! Sign in instead!");
                signInWarning.setVisible(true);
                signInWarning.setManaged(true);
                return;
            }

            // Perform database operations safely inside the try block
            String salt = PDKF2.getSalt();
            DatabaseOperations.insertToMasterDB(getHashedMasterPass(), salt);

            // Update UI upon successful database insert
            masterPasswordField.clear();
            masterPasswordField.setPromptText("MASTER PASSWORD SAVED SUCCESSFULLY");
            switchToSignInScene(event);

        } catch (SQLException e) {
            e.printStackTrace();
            signInWarning.setText("Database error occurred. Please try again.");
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

    public void setMasterKey(ActionEvent event) throws IOException {
        if (passwordsMatch()) insertIntoDB(event);
        else {
            signInWarning.setText("New password doesn't match!");
            signInWarning.setVisible(true);
            signInWarning.setManaged(true);
        }
    }
}
