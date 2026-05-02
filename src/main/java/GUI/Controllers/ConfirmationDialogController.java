package GUI.Controllers;

import GUI.Utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfirmationDialogController {
    @FXML
    public Label bodyText;

    @FXML
    public Button cancelButton;

    @FXML
    public Button confirmButton;

    @FXML
    public Label headerText;

    public void setup(String header, String body, String confirmText, Runnable onConfirm) {
        headerText.setText(header);
        bodyText.setText(body);
        confirmButton.setText(confirmText);

        confirmButton.setOnAction(event -> {
            onConfirm.run();
            ((Stage)confirmButton.getScene().getWindow()).close();
        });
    }

    @FXML
    void cancelAction() {
        ((Stage)cancelButton.getScene().getWindow()).close();
    }
}
