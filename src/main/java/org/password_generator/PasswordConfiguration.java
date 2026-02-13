package org.password_generator;

import java.util.ArrayList;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.Rule;

public class PasswordConfiguration {
    private final ArrayList<Rule> rules;

    public PasswordConfiguration(boolean hasDigit, boolean hasSpecialChar, boolean mixedCase) {
        rules = new ArrayList<>();

        if (hasDigit) {rules.add(new CharacterRule(EnglishCharacterData.Digit));}
        if (hasSpecialChar) {rules.add(new CharacterRule(EnglishCharacterData.SpecialAscii));}
        if (mixedCase) {rules.add(new CharacterRule(EnglishCharacterData.Alphabetical));}
        else {rules.add(new CharacterRule(EnglishCharacterData.LowerCase));}
    }

    public ArrayList<Rule> getRules() {return rules;}
}