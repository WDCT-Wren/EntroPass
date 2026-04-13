package GUI.Controllers;

import GUI.Utils.SceneUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {;

    @FXML
    void switchToPasswordBuilder(MouseEvent event) throws IOException {
        String fxmlFile = "PasswordBuilder.fxml";
        String cssFile = "BuilderStyleSheet.css";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SceneUtils.getScene(stage, fxmlFile, cssFile);
    }

    @FXML
    void switchToPasswordVault(MouseEvent event) throws IOException {
        String fxmlFile = "PasswordVault.fxml";
        String cssFile = "VaultStyleSheet.css";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SceneUtils.getScene(stage, fxmlFile, cssFile);
    }
}
