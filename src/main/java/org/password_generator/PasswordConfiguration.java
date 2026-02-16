package org.password_generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.Rule;

/**
 * Class that evaluates the user's desired password configuration
 */
public class PasswordConfiguration {
    private final List<Rule> rules;
    private final boolean hasDigit;
    private final boolean hasSpecialChar;
    private final boolean hasMixedCase;

    /**
     * Makes the configuration of the password based on the user's preferences.
     *
     * @param hasDigit If the user wants numbers in their password
     * @param hasSpecialChar If the user wants a special character in their password
     * @param mixedCase If the user wants a mix of uppercase and lowercase letters in their password
     */
    public PasswordConfiguration(boolean hasDigit, boolean hasSpecialChar, boolean mixedCase) {
        rules = new ArrayList<>();
        this.hasDigit = hasDigit;
        this.hasSpecialChar = hasSpecialChar;
        this.hasMixedCase = mixedCase;

        if (hasDigit) rules.add(new CharacterRule(EnglishCharacterData.Digit));
        if (hasSpecialChar) rules.add(new CharacterRule(EnglishCharacterData.SpecialAscii));
        if (mixedCase) rules.add(new CharacterRule(EnglishCharacterData.Alphabetical));
        else rules.add(new CharacterRule(EnglishCharacterData.LowerCase));
    }

    /**
     * @return A copy of the configuration list
     */
    public List<Rule> getRules() {
        return Collections.unmodifiableList(rules);
    }

    //Getter methods for each boolean.
    public boolean hasDigit() {return hasDigit;}
    public boolean hasSpecialChar() {return hasSpecialChar;}
    public boolean hasMixedCase() {return hasMixedCase;}
}