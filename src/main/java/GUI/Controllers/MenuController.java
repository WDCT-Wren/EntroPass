package GUI.Controllers;

import GUI.Utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {;

    @FXML
    void switchToPasswordBuilder(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneUtils.getScene(stage, "PasswordBuilder.fxml");
    }

    @FXML
    void switchToPasswordVault(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneUtils.getScene(stage, "PasswordVault.fxml");
    }

    @FXML
    void openAboutPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneUtils.getScene(stage, "AboutPage.fxml");
    }
}
