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
    public int getPasswordLength() {
        return Integer.parseInt(digitField.getText());
    }

    @FXML
    private void onButtonClick() {
        PasswordConfiguration password = new PasswordConfiguration(numberCheckBox.isSelected(), specialCharCheckBox.isSelected(), mixedCaseCheckBox.isSelected());
        try {
            int passwordLength = getPasswordLength();
            PasswordBuilder builder = new PasswordBuilder(passwordLength, password);

            if (passwordLength < 8 || passwordLength > 128) {
                passwordText.setText("Password length must be between 8 and 128");
                return;
            }
            passwordText.setText(builder.passwordBuilder(passwordLength, password));
            PasswordStrengthChecker strength = new PasswordStrengthChecker(builder);
            passwordStrength.setText(String.valueOf(strength.checkStrength(passwordLength, numberCheckBox.isSelected(), specialCharCheckBox.isSelected(), mixedCaseCheckBox.isSelected())));
        }
        catch (NumberFormatException e) {
            passwordText.setText("Please enter a valid number");
        }

    }
}
