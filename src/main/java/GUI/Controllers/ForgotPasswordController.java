package GUI.Controllers;

import Database.DatabaseOperations;
import Database.UserDAO;
import GUI.Utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ForgotPasswordController {

    @FXML
    private CheckBox acknowledgementCheckbox;

    @FXML
    private Label storedAssetsCounter;

    @FXML
    private Button resetButton;

    UserDAO userDAO = new UserDAO();

    private void initialize() {
        // Prevents the player from clicking reset if the checkbox was not clicked
        resetButton.disableProperty().bind(acknowledgementCheckbox.selectedProperty().not());
        storedAssetsCounter.setText(String.valueOf(userDAO.getRowCount()));
    }

    @FXML
    void resetVault(ActionEvent event) throws SQLException, IOException {
        DatabaseOperations.purgeDB();
        switchToAuthScene(event);
    }

    @FXML
    void switchToAuthScene(ActionEvent event) throws IOException {
        String fxmlFile = "AuthMenu.fxml";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SceneUtils.getScene(stage, fxmlFile);
    }

}
