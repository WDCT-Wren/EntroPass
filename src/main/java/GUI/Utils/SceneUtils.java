package GUI.Utils;

import GUI.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneUtils {
    /**
     * Utility method that lets you load a scene from a fxml file and a css file
     * @param stage the stage that the scene will be loaded into
     * @param fxmlFile the fxml file that will be loaded
     * @param cssFile the CSS file that will be loaded
     */
    @FXML
    public static void getScene(Stage stage, String fxmlFile, String cssFile) throws IOException {
        String fxmlSourceRoot = "/org/password_generator_gui/Scenes/" + fxmlFile;
        String cssSourceRoot = "/org/password_generator_gui/Stylesheets/" + cssFile;

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxmlSourceRoot));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(SceneUtils.class.getResource(cssSourceRoot)).toExternalForm());
        stage.setTitle("EntroPass 0.67");
        stage.setScene(scene);
        stage.show();
    }
}
