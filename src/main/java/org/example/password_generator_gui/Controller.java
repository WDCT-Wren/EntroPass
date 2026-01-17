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

    int passwordLength;

    public int getPasswordLength() {
        try {
            passwordLength = Integer.parseInt(digitField.getText());
        }
        catch (NumberFormatException e) {
            passwordText.setText("Invalid Input");
        }
        return passwordLength;
    }

    @FXML
    private void onButtonClick() {
        passwordText.setText(passwordBuilder.passwordGenerator(getPasswordLength(), lowerCaseCheckBox.isSelected(),
                upperCaseCheckBox.isSelected(), numberCheckBox.isSelected(), specialCharCheckBox.isSelected()));
    }
}
