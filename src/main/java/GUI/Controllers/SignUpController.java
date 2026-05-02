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
    public void initialize() {
        signInWarning.setVisible(false);

        masterPasswordField.setOnMouseClicked(event -> {
            signInWarning.setVisible(false);
        });

        masterPasswordField.textProperty().addListener(
                StrengthUIHelper.manualStrengthListener(masterKeyStrength));
    }

    @FXML
    private void insertIntoDB(ActionEvent event) throws SQLException, IOException {
        boolean invalidPassword = getMasterPass().isEmpty();
        String salt = PDKF2.getSalt();

        if (!invalidPassword) {
            DatabaseOperations.insertToMasterDB(getHashedMasterPass(), salt);
            masterPasswordField.clear();
            masterPasswordField.setPromptText("MASTER PASSWORD SAVED SUCCESSFULLY");
            switchToSignInScene(event);
        }
        else masterPasswordField.setPromptText("MASTER PASSWORD CANNOT BE EMPTY");
    }

    @FXML
    void switchToSignInScene(ActionEvent event) throws IOException {
        boolean masterPasswordExists = MasterDAO.retrieveMasterPass() != null;
        String fxmlFile = "AuthMenu.fxml";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (masterPasswordExists) SceneUtils.getScene(stage, fxmlFile);
        else signInWarning.setVisible(true);
    }

    // helper getter methods
    private String getHashedMasterPass() { return BCrypt.hashpw(masterPasswordField.getText(), BCrypt.gensalt());}
    private String getMasterPass() { return masterPasswordField.getText();}
    private int getMasterKeyLength() {return masterPasswordField.getLength();}
    public void setMasterKey(ActionEvent event) throws SQLException, IOException {
        insertIntoDB(event);
    }
}
