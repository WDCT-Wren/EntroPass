package org.example.password_generator_gui;

import java.security.SecureRandom;

public class Password {
    private static final String LOWER_CASES = "abcdefghijklmnopqrstuvwxyz"; //String containing all possible lowercase characters
    private static final String UPPER_CASES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //String containing all possible uppercase characters
    private static final String NUMERICAL_CHARS = "0123456789"; //String containing all possible numerical values
    private static final String SPECIAL_CHARS = "!@#$%^&*:';_-"; //String containing commonly used special characters

    public String passwordBuilder(int passLength, boolean hasLowerCase, boolean hasNumbers, boolean hasSpecialChars, boolean hasUpperCase) {
        StringBuilder charPoolBuilder = new StringBuilder();
        int requiredCount = 0;

        if (hasLowerCase) {
            charPoolBuilder.append(LOWER_CASES);
            requiredCount++;
        }
        if (hasUpperCase) {
            charPoolBuilder.append(UPPER_CASES);
            requiredCount++;
        }
        if (hasNumbers) {
            charPoolBuilder.append(NUMERICAL_CHARS);
            requiredCount++;
        }
        if (hasSpecialChars) {
            charPoolBuilder.append(SPECIAL_CHARS);
            requiredCount++;
        }

        if (passLength < requiredCount) {
            throw new IllegalArgumentException("Password length must be at least " + requiredCount);
        }
        if (requiredCount <= 0) {
            throw new IllegalArgumentException("At least one character type must be selected");
        }

        String charPoolString = charPoolBuilder.toString();
        SecureRandom random = new SecureRandom();

        while (true) {
            StringBuilder passwordBuilder = new StringBuilder();
            for (int i = 0; i < passLength; i++) {
                int rand = random.nextInt(charPoolString.length());
                passwordBuilder.append(charPoolString.charAt(rand));
            }

            String password = passwordBuilder.toString();
            if (isValidPassword(password, hasLowerCase, hasNumbers, hasSpecialChars, hasUpperCase)) {
                return password;
            }
        }
    }
    private boolean passwordValidator (String password, boolean hasLowerCase, boolean hasNumbers, boolean hasSpecialChars, boolean hasUpperCase) {
        boolean hasNumber = password.matches(".*[0-9].*"); //Regex check if the password contains a number
        boolean hasLower = password.matches(".*[a-z].*"); //Regex check if the password contains a lowercase letter
        boolean hasCapital = password.matches(".*[A-Z].*"); //Regex check if the password contains a capital letter
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*:';_-].*"); //Regex check if the password contains a special character

        if (hasLowerCase && !hasLower) {return false;}
        if (hasNumbers && !hasNumber) {return false;}
        if (hasUpperCase && !hasCapital) {return false;}
        if (hasSpecialChars && !hasSpecialChar) {return false;}

        return hasLowerCase || hasNumbers ||  hasUpperCase|| hasSpecialChars;
    }
    //Getter Method
    public boolean isValidPassword(String password, boolean hasLowerCase, boolean hasUpperCase, boolean hasNumbers, boolean hasSpecialChars) {
        return passwordValidator(password, hasLowerCase, hasUpperCase, hasNumbers, hasSpecialChars);
    }
}