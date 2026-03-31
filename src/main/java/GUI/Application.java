package GUI;

import Database.DatabaseManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;


public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            DatabaseManager.getInstance().initDatabase();
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/org/password_generator_gui/Scenes/AuthMenu.fxml"));
            Region root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("EntroPass 0.67");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        DatabaseManager.getInstance().closeConnection();
    }
}
