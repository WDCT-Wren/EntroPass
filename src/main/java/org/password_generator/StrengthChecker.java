package org.password_generator;

/**
 * Class that evaluates a given password's strength.
 * Uses the <b>Password Entropy Formula</b> to calculate the password's Strength.
 * <br><br>
 */
public class StrengthChecker {
    private final static int DIGIT_POOL_SIZE = 10;
    private final static int SPECIAL_CHAR_POOL_SIZE = 32;
    private final static int MIXED_CASE_POOL_SIZE = 52;
    private final static int LOWER_CASE_POOL_SIZE = 26;

    private final static int VERY_STRONG_THRESHOLD = 325;
    private final static int STRONG_THRESHOLD = 100;
    private final static int MEDIUM_THRESHOLD = 72;
    private final static int WEAK_THRESHOLD = 44;

    private final static double MIN_ENTROPY = 37.60351774512874;
    private final static double MAX_ENTROPY = 419.4936865073688;

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
    public static double getStrength(Configurator config, int passwordLength) {
        int charPoolSize = 0;

        if (config.hasDigit()) charPoolSize += DIGIT_POOL_SIZE;
        if (config.hasSpecialChar()) charPoolSize += SPECIAL_CHAR_POOL_SIZE;
        if (config.hasMixedCase()) charPoolSize += MIXED_CASE_POOL_SIZE;
        else charPoolSize += LOWER_CASE_POOL_SIZE;

        return passwordLength * log2(charPoolSize);
    }

    public static double getNormalStrength(double initialStrength) {
        return (initialStrength - MIN_ENTROPY)/(MAX_ENTROPY - MIN_ENTROPY);
    }

    public static String checkStrength(double strength) {
        if (strength >= VERY_STRONG_THRESHOLD) return "Very Strong";
        else if (strength >= STRONG_THRESHOLD) return "Strong";
        else if (strength >= MEDIUM_THRESHOLD) return "Medium";
        else if (strength >= WEAK_THRESHOLD) return "Weak";
        else return "Very Weak";
    }

    /**
     * Calculates the base-2 logarithm for a positive integer.
     * Returns 0 for an input of 1. Undefined for n <= 0.
     */
    private static double log2(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        return Math.log(n) / Math.log(2);
    }
}
