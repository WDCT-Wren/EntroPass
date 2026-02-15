package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.password_generator.PasswordBuilder;
import org.password_generator.PasswordConfiguration;
import org.password_generator.PasswordStrengthChecker;

public class Controller {
    @FXML
    private Label passwordText;
    @FXML
    private TextField digitField;
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
        digitField.setText("8");

        //prevents users from entering anything other than numbers.
        digitField.textProperty().addListener((_, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //if the new value does not match any possible digit through regex checking
                digitField.setText(oldValue); //the digitField would be set back to the old value
            }
        });
    }

    //Method that gets the Password length from the digit field of the GUI program
    private int getPasswordLength() {return Integer.parseInt(digitField.getText());}

    @FXML
    private void onButtonClick() {
        PasswordConfiguration configuration = new PasswordConfiguration(numberCheckBox.isSelected(), specialCharCheckBox.isSelected(), mixedCaseCheckBox.isSelected());
        try {
            int passwordLength = getPasswordLength();
            setPassword(passwordLength, configuration);
            setPasswordStrength(passwordLength, configuration);
        }
        catch (NumberFormatException e) {
            passwordText.setText("Please enter a valid number");
        }
    }

    private void setPassword(int passwordLength, PasswordConfiguration configuration) {
        PasswordBuilder builder = new PasswordBuilder(passwordLength);
        passwordText.setText(builder.buildPassword(configuration));
    }

    private void setPasswordStrength(int passwordLength, PasswordConfiguration configuration) {
        passwordStrength.setText(
                PasswordStrengthChecker.
                checkStrength(configuration, passwordLength)
        );
    }
}
