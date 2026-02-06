package org.example.password_generator_gui;

import java.util.ArrayList;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.passay.Rule;

public class PasswordBuilder {
    PasswordConfiguration config;
    PasswordGenerator generator;
    int passwordLength;

    public PasswordBuilder(int passwordLength, PasswordConfiguration config) {
        this.config = config;
        this.passwordLength = passwordLength;
        this.generator = new PasswordGenerator();
    }

    public String passwordBuilder(int passwordLength, PasswordConfiguration config) {
        return generator.generatePassword(passwordLength, config.getRules());
    }
}
