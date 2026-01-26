package org.example.password_generator_gui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
    Password password = new Password();
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
        boolean anySelected = lowerCaseCheckBox.isSelected()
                || upperCaseCheckBox.isSelected()
                || specialCharCheckBox.isSelected()
                || numberCheckBox.isSelected();

        Password password = new Password();
        try {
            int passwordLength = getPasswordLength();

            if (passwordLength < 8 || passwordLength > 128) {
                passwordText.setText("Password length must be between 8 and 128");
                return;
            }
            if (!anySelected) {
                passwordText.setText("At least one character type must be selected");
                return;
            }
            passwordText.setText(password.passwordBuilder(passwordLength, lowerCaseCheckBox.isSelected(), numberCheckBox.isSelected(),
                    specialCharCheckBox.isSelected(), upperCaseCheckBox.isSelected()));
        }
        catch (NumberFormatException e) {
            passwordText.setText("Please enter a valid number");
        }
    }
}
