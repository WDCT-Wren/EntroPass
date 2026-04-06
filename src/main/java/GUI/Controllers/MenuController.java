package GUI.Controllers;

import GUI.Application;
import GUI.Utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuController {
    @FXML
    private Button GenerateNewPassword;
    @FXML
    private Button ViewSavedPassword;

    @FXML
    void switchToPasswordBuilder(ActionEvent event) throws IOException {
        String fxmlFile = "PasswordBuilder.fxml";
        String cssFile = "BuilderStyleSheet.css";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SceneUtils.switchScene(stage, fxmlFile, cssFile);
    }

    @FXML
    void switchToPasswordVault(ActionEvent event) throws IOException {
        String fxmlFile = "PasswordVault.fxml";
        String cssFile = "VaultStyleSheet.css";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SceneUtils.switchScene(stage, fxmlFile, cssFile);
    }
}
