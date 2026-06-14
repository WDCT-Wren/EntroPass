package GUI.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class ThirdPartyLicensesController implements Initializable {

    @FXML
    private TextArea jbcryptTextArea;

    @FXML
    private TextArea apacheTextArea;

    @FXML
    private TextArea javafxTextArea;

    private static final String LICENSE_BASE_PATH = "/org/Assets/Licenses/";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jbcryptTextArea.setText(loadResource("LICENSE_JBCrypt.txt"));

        String apacheLicense = loadResource("LICENSE_Apache2.0.txt");
        String passayNotice = loadResource("NOTICE_Passay.txt");
        String sqliteNotice = loadResource("NOTICE_SQLite_JDBC.txt");

        StringBuilder apacheCombined = new StringBuilder();
        apacheCombined.append(apacheLicense);
        apacheCombined.append("\n\n");
        apacheCombined.append("----------------------------------------\n");
        apacheCombined.append("The following NOTICE is reproduced from Passay\n");
        apacheCombined.append("as required by the Apache License, Version 2.0:\n");
        apacheCombined.append("----------------------------------------\n\n");
        apacheCombined.append(passayNotice);
        apacheCombined.append("\n\n");
        apacheCombined.append("----------------------------------------\n");
        apacheCombined.append("The following NOTICE is reproduced from sqlite-jdbc\n");
        apacheCombined.append("as required by the Apache License, Version 2.0:\n");
        apacheCombined.append("----------------------------------------\n\n");
        apacheCombined.append(sqliteNotice);

        apacheTextArea.setText(apacheCombined.toString());

        javafxTextArea.setText(loadResource("LICENSE_JavaFX.txt"));
    }

    private String loadResource(String fileName) {
        String fullPath = LICENSE_BASE_PATH + fileName;
        try (InputStream inputStream = getClass().getResourceAsStream(fullPath)) {
            if (inputStream == null) {
                return "[Unable to load " + fileName + " — resource not found at " + fullPath + "]";
            }
            return readAll(inputStream);
        } catch (IOException e) {
            return "[Error reading " + fileName + ": " + e.getMessage() + "]";
        }
    }

    private String readAll(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        }
        return builder.toString();
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) jbcryptTextArea.getScene().getWindow();
        stage.close();
    }
}