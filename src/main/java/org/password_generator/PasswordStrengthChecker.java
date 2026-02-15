package org.password_generator;

public class PasswordStrengthChecker {
    private final static int DIGIT_POOL_SIZE = 10;
    private final static int SPECIAL_CHAR_POOL_SIZE = 32;
    private final static int MIXED_CASE_POOL_SIZE = 52;
    private final static int LOWER_CASE_POOL_SIZE = 26;

    private final static int STRONG_THRESHOLD = 100;
    private final static int MEDIUM_THRESHOLD = 72;
    private final static int WEAK_THRESHOLD = 44;

    public static String checkStrength(PasswordConfiguration config, int passwordLength) {
        int bitSize = 0;
        if (config.hasDigit()) bitSize += DIGIT_POOL_SIZE;
        if (config.hasSpecialChar()) bitSize += SPECIAL_CHAR_POOL_SIZE;
        if (config.hasMixedCase()) bitSize += MIXED_CASE_POOL_SIZE;
        else bitSize += LOWER_CASE_POOL_SIZE;

        int finalStrength = passwordLength * floorLog2(bitSize);

        if (finalStrength >= STRONG_THRESHOLD) {return "Strong";}
        else if (finalStrength >= MEDIUM_THRESHOLD) {return "Medium";}
        else if (finalStrength >= WEAK_THRESHOLD) {return "Weak";}
        else {return "Very Weak";}
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
}
