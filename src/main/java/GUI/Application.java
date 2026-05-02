package GUI;

import Database.DatabaseManager;
import Database.MasterDAO;
import GUI.Utils.SceneUtils;
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

        try {
            DatabaseManager.getInstance().initDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean masterPasswordExist = MasterDAO.retrieveMasterPass() != null;
        try {
            String fxmlFile = masterPasswordExist ? "AuthMenu.fxml" : "SignUpScene.fxml";
            SceneUtils.getScene(stage, fxmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        DatabaseManager.getInstance().closeConnection();
    }
}
