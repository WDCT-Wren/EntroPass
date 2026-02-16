package org.password_generator;

/**
 * Class that evaluates a given password's strength.
 * Uses the <b>Password Entropy Formula</b> to calculate the password's Strength.
 * <br><br>
 */
public class PasswordStrengthChecker {
    private final static int DIGIT_POOL_SIZE = 10;
    private final static int SPECIAL_CHAR_POOL_SIZE = 32;
    private final static int MIXED_CASE_POOL_SIZE = 52;
    private final static int LOWER_CASE_POOL_SIZE = 26;

    private final static int STRONG_THRESHOLD = 100;
    private final static int MEDIUM_THRESHOLD = 72;
    private final static int WEAK_THRESHOLD = 44;

    /**
     * Computes and interprets the generated password's strength.
     * Using the <b>Password Entropy Formula</b> or <b>(passwordLength)*(Log2(charPoolSize))</b>.
     * <br><br>
     * It calculates the total entropy of the generated password in bits
     * and evaluates the bitSize with different thresholds where it should at least be <b>72</b> bits to be considered a viable password.
     *
     * @param config if the password were to have a digit, special character, or a capital letter.
     * @param passwordLength the length of the password
     * @return
     * <list>
     *       <ul><b>"Strong"</b></ul>
     *       <ul><b>"Medium"</b></ul>
     *       <ul><b>"Weak"</b></ul>
     *       <ul><b>"Very Weak"</b></ul>
     * </list>
     */
    public static String checkStrength(PasswordConfiguration config, int passwordLength) {
        int charPoolSize = 0;

        if (config.hasDigit()) charPoolSize += DIGIT_POOL_SIZE;
        if (config.hasSpecialChar()) charPoolSize += SPECIAL_CHAR_POOL_SIZE;
        if (config.hasMixedCase()) charPoolSize += MIXED_CASE_POOL_SIZE;
        else charPoolSize += LOWER_CASE_POOL_SIZE;

        int finalStrength = passwordLength * floorLog2(charPoolSize);

        if (finalStrength >= STRONG_THRESHOLD) return "Strong";
        else if (finalStrength >= MEDIUM_THRESHOLD) return "Medium";
        else if (finalStrength >= WEAK_THRESHOLD) return "Weak";
        else return "Very Weak";
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
