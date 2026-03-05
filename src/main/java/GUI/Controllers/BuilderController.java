package GUI.Controllers;

import Database.PasswordHasher;
import Database.UserRepository;
import GUI.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.password_generator.Builder;
import org.password_generator.Configurator;
import org.password_generator.StrengthChecker;

import java.io.IOException;
import java.sql.SQLException;

public class BuilderController {
    @FXML
    private ProgressBar strengthIndicator;
    @FXML
    private Slider passwordLengthSlider;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField serviceNameField;
    @FXML
    private TextField noteField;
    @FXML
    private Label passwordText;
    @FXML
    private TextField passLength;
    @FXML
    private CheckBox mixedCaseCheckBox;
    @FXML
    private CheckBox specialCharCheckBox;
    @FXML
    private CheckBox numberCheckBox;
    @FXML
    private Label passwordStrength;

    @FXML
    private void initialize() {
        if (passLength != null) {
            passLength.setText("8");

            //prevents users from entering anything other than numbers.
            passLength.textProperty().addListener((_, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) { //if the new value does not match any possible digit through regex checking
                    passLength.setText(oldValue); //the passLength would be set back to the old value
                }
            });
        }

        passwordLengthSlider.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newValue) {
                if (newValue != null) {
                    passLength.setText(String.valueOf(newValue.intValue()));
                }
            }
        });

        strengthIndicator.setStyle("-fx-accent: #64ED86;");
    }

    @FXML
    public void switchToMenuScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/org/password_generator_gui/Scenes/StartingMenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void generatePassword() {
        Configurator configuration = new Configurator(numberCheckBox.isSelected(), specialCharCheckBox.isSelected(), mixedCaseCheckBox.isSelected());
        try {
            if (getPasswordLength() < 8 || getPasswordLength() > 128) passwordText.setText("Length must be between 8 and 128 characters!");

            int passwordLength = getPasswordLength();
            setPassword(passwordLength, configuration);
            setPasswordStrength(configuration, passwordLength);
        }
        catch (NumberFormatException e) {
            passwordText.setText("Please enter a valid number");
        }
    }

    private void setPassword(int passwordLength, Configurator configuration) {
        Builder builder = new Builder(passwordLength);
        String password = builder.buildPassword(configuration);
        passwordText.setText(password);

        if (password.length() > 28) {
            double baseSize = 16.0;
            double newSize = Math.max(7.0, baseSize * (28.0 / password.length()));
            passwordText.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-font-weight: bold; -fx-font-size: " + String.format("%.1f", newSize) + ";");
        } else {
            passwordText.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-font-weight: bold; -fx-font-size: 16.0;");
        }
    }

    private void setPasswordStrength(Configurator configuration, int passwordLength) {
        double strength = StrengthChecker.getStrength(configuration, passwordLength);

        passwordStrength.setText(
                StrengthChecker.
                checkStrength(strength)
        );

        strengthIndicator.setProgress(StrengthChecker.getNormalStrength(strength));
        System.out.println("initial Strength: " + strength);
        System.out.println(StrengthChecker.getNormalStrength(strength));
    }

    @FXML
    private void savePassword() throws SQLException {
        UserRepository newRepo = new UserRepository(getUsername(), getServiceName(), getHashedPassword(), getNote());
        newRepo.insertPassword();

        passwordText.setText("Password Saved Successfully!");
    }

    //Getter Methods.
    private int getPasswordLength() {return Integer.parseInt(passLength.getText());}
    private String getUsername() {
        if (usernameField == null) {
            return "";
        }
        return usernameField.getText();
    }
    private String getServiceName() {
        if (serviceNameField == null) {
            return "";
        }
        return serviceNameField.getText();
    }
    private String getNote() {
        if (noteField == null) {
            return "";
        }
        return noteField.getText();
    }
    private String getHashedPassword() {return PasswordHasher.hashPassword(passwordText.getText());}
}
