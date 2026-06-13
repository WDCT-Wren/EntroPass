package GUI;

import Database.DatabaseManager;
import Database.MasterDAO;
import GUI.Utils.SceneUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/Assets/Images/EntroPass_Logo.png")));

        stage.setWidth(1316);
        stage.setHeight(900);
        stage.setResizable(false);
        stage.getIcons().add(icon);

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
