package org.example.password_generator_gui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
    PasswordBuilder passwordBuilder = new PasswordBuilder();
    @FXML
    private Label passwordText;
    @FXML
    private TextField digitField;
    @FXML
    private CheckBox lowerCaseCheckBox;
    @FXML
    private CheckBox upperCaseCheckBox;
    @FXML
    private CheckBox specialCharCheckBox;
    @FXML
    private CheckBox numberCheckBox;
    @FXML
    private void initialize() {
        digitField.setText("8");

        digitField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //if the new value does not match any possible digit through regex checking
                digitField.setText(oldValue); //the digitField would be set back to the old value
            }
        });
    }

    public int getPasswordLength() {
        return Integer.parseInt(digitField.getText());
    }

    @FXML
    private void onButtonClick() {
        try {
            int passwordLength = getPasswordLength();

            if (passwordLength < 8 || passwordLength > 128) {
                passwordText.setText("Password length must be between 8 and 128");
                return;
            }
            passwordText.setText(passwordBuilder.passwordGenerator(passwordLength, lowerCaseCheckBox.isSelected(),
                    upperCaseCheckBox.isSelected(), numberCheckBox.isSelected(), specialCharCheckBox.isSelected()));
        }
        catch (NumberFormatException e) {
            passwordText.setText("Please enter a valid number");
        }
    }
}
