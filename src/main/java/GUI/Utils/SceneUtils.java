package GUI.Utils;

import GUI.Application;
import GUI.Controllers.ConfirmationDialogController;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.Password_Generator.StrengthChecker;

import java.io.IOException;
import java.util.Objects;

public class SceneUtils {
    /**
     * Utility method that lets you load a scene from a fxml file.
     * @param stage the stage that the scene will be loaded into
     * @param fxmlFile the fxml file that will be loaded
     */
    @FXML
    public static void getScene(Stage stage, String fxmlFile) throws IOException {
        double width = stage.getWidth();
        double height = stage.getHeight();

        String fxmlSourceRoot = "/org/password_generator_gui/Scenes/" + fxmlFile;

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxmlSourceRoot));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("EntroPass 0.67");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Shows a popup window with the given fxml file.
     * @param fxmlFile the fxml file that will be loaded
     * @throws IOException if the fxml file cannot be found
     */
    @FXML
    public static void showWindow(String fxmlFile, String header, String body, String buttonText, Runnable onConfirm) throws IOException {
        String fxmlSourceRoot = "/org/password_generator_gui/Scenes/" + fxmlFile;
        // call the new FXML file
        FXMLLoader loader = new FXMLLoader(SceneUtils.class.getResource(fxmlSourceRoot));
        Parent root = loader.load();
        ConfirmationDialogController controller = loader.getController();
        controller.setup(header, body, buttonText, onConfirm);

        // Create the new Stage
        Stage dialog = new Stage();
        Scene scene = new Scene(root);
        dialog.setScene(scene);

        // Make the stage transparent
        scene.setFill(Color.TRANSPARENT);
        dialog.initStyle(StageStyle.TRANSPARENT);

        // Make it a modal window
        dialog.initModality(Modality.APPLICATION_MODAL);

        // Show and wait for user action
        dialog.showAndWait();
    }

    /**
     * Listener util to sync both password fields and textfield in the password entry
     * @param hiddenPassword the password field with hidden text
     * @param visiblePassword the password field's visible text counterpart
     * @return synchronizes the text of both fields for the password entry
     */
    public static ChangeListener<String> synchronizePasswordFields (PasswordField hiddenPassword, TextField visiblePassword) {
        return (observable, oldValue, newValue) -> {
            hiddenPassword.setText(newValue);
            visiblePassword.setText(newValue);
        };
    }
}
