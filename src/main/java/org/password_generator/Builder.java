package org.password_generator;

import org.passay.PasswordGenerator;


public class Builder {
    private final PasswordGenerator generator;
    private final int passwordLength;

    public Builder(int passwordLength) {
        this.passwordLength = passwordLength;
        this.generator = new PasswordGenerator();

        if (passwordLength < 8 || passwordLength > 128) throw new IllegalArgumentException("Password length must be between 8 and 128 characters!");
    }

    public String buildPassword(Configurator config) {
        return generator.generatePassword(this.passwordLength, config.getRules());
    }
}
