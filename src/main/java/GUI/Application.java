package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/org/password_generator_gui/Scenes/PasswordBuilderScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Password Generator 1.2");
        stage.setScene(scene);
        stage.show();
    }
}
