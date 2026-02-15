package org.password_generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.Rule;

public class PasswordConfiguration {
    private final List<Rule> rules;
    private final boolean hasDigit;
    private final boolean hasSpecialChar;
    private final boolean hasMixedCase;

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

    //Getter method for a copy of the arraylist
    public List<Rule> getRules() {
        return Collections.unmodifiableList(rules);
    }

    //Getter methods for each boolean.
    public boolean hasDigit() {return hasDigit;}
    public boolean hasSpecialChar() {return hasSpecialChar;}
    public boolean hasMixedCase() {return hasMixedCase;}
}