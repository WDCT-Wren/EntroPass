package GUI.Controllers;

import GUI.Utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AboutController {

    @FXML
    void backToHomePage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneUtils.getScene(stage, "StartingMenu.fxml");
    }

    @FXML
    private void openThirdPartyLicenses() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/org/password_generator_gui/Scenes/ThirdPartyLicensesScene.fxml"));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Third-Party Licenses");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
