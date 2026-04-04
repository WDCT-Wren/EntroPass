package GUI;

import Database.DatabaseManager;
import Database.MasterDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;


public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Load fonts in the application
        Font.loadFont(getClass().getResourceAsStream("org/Assets/Fonts/SpaceGrotesk-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("org/Assets/Fonts/SpaceGrotesk-Bold.ttf"), 16);

        boolean masterPasswordExist = MasterDAO.retrieveMasterPass() != null;
        if (masterPasswordExist) {
            try {
                DatabaseManager.getInstance().initDatabase();
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/org/password_generator_gui/Scenes/AuthMenu.fxml"));
                Region root = fxmlLoader.load();
                Scene scene = new Scene(root);
                stage.setTitle("EntroPass 0.67");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                DatabaseManager.getInstance().initDatabase();
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/org/password_generator_gui/Scenes/SignUpScene.fxml"));
                Region root = fxmlLoader.load();
                Scene scene = new Scene(root);
                stage.setTitle("EntroPass 0.67");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        DatabaseManager.getInstance().closeConnection();
    }
}
