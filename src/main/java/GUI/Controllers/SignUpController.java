package GUI.Controllers;

import Database.DatabaseOperations;
import Database.MasterDAO;
import Encryption.PDKF2;
import GUI.Utils.SceneUtils;
import GUI.Utils.StrengthUIHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import org.Password_Generator.Configurator;
import org.Password_Generator.StrengthChecker;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {

    @FXML
    private Label signInWarning;

    @FXML
    private ProgressBar masterKeyStrength;

    @FXML
    private PasswordField masterPasswordField;

    @FXML
    private Label passwordStrength;

    @FXML
    public void initialize() {
        signInWarning.setVisible(false);
        signInWarning.setManaged(false);

        masterPasswordField.setOnMouseClicked(event -> {
            signInWarning.setVisible(false);
            signInWarning.setManaged(false);
        });

        masterPasswordField.textProperty().addListener(
                StrengthUIHelper.manualStrengthListener(masterKeyStrength, passwordStrength));
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
    void switchToSignInScene(ActionEvent event) throws IOException {
        boolean masterPasswordExists = MasterDAO.retrieveMasterPass() != null;
        String fxmlFile = "AuthMenu.fxml";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (masterPasswordExists) SceneUtils.getScene(stage, fxmlFile);
        else {
            signInWarning.setText("You don't have a stored account yet! Make one first before you can sign in!");
            signInWarning.setVisible(true);
            signInWarning.setManaged(true);
        }
    }

    // helper getter methods
    private String getHashedMasterPass() { return BCrypt.hashpw(masterPasswordField.getText(), BCrypt.gensalt());}
    private String getMasterPass() { return masterPasswordField.getText();}
    public void setMasterKey(ActionEvent event) throws SQLException, IOException {insertIntoDB(event);}
}
