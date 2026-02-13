package org.password_generator;

public class PasswordStrengthChecker {
    PasswordConfiguration config;
    PasswordBuilder builder;
    private final static int DIGIT_CHARS = 10;
    private final static int SPECIAL_CHARS = 32;
    private final static int LOWERCASE_CHARS = 26;
    private final static int MIXED_CASE_CHARS = 52;

    public PasswordStrengthChecker(PasswordBuilder builder) {
        this.builder = builder;
        this.config = builder.config;

    }
    /**
     * Calculates the floor of the base-2 logarithm for a positive integer.
     * Returns 0 for an input of 1. Undefined for n <= 0.
     */
    private static int floorLog2(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        // 31 - number of leading zeros gives the position of the highest set bit
        return 31 - Integer.numberOfLeadingZeros(n);
    }

    public String checkStrength(int passwordLength, boolean hasDigit, boolean hasSpecialChar, boolean mixedCase) {
        int charPool = 0;
        if (hasDigit) {charPool += DIGIT_CHARS;}
        if (hasSpecialChar) {charPool += SPECIAL_CHARS;}
        if (mixedCase) {charPool += MIXED_CASE_CHARS;}
        else {charPool += LOWERCASE_CHARS;}

        int finalStrength = passwordLength * floorLog2(charPool);

        if (finalStrength >= 100) {return "Strong";}
        else if (finalStrength >= 72) {return "Medium";}
        else {return "Weak";}
    }


}
