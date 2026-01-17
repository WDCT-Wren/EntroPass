package org.example.password_generator_gui;

public class PasswordBuilder {
    private static final String lowerCases = "abcdefghijklmnopqrstuvwxyz"; //String containing all possible lowercase characters
    private static final String upperCases = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //String containing all possible uppercase characters
    private static final String numericalChars = "0123456789"; //String containing all possible numerical values
    private static final String specialChars = "!@#$%^&*:';_-"; //String containing commonly used special characters

    public String passwordGenerator(int digit, boolean hasLowerCase, boolean hasNumbers, boolean hasSpecialChars, boolean hasUpperCase) {

        StringBuilder charPoolBuilder = new StringBuilder();
        if (hasLowerCase) charPoolBuilder.append(lowerCases);
        if (hasUpperCase) charPoolBuilder.append(upperCases);
        if (hasNumbers) charPoolBuilder.append(numericalChars);
        if (hasSpecialChars) charPoolBuilder.append(specialChars);

        String charPoolString = charPoolBuilder.toString();
        String password = "";
        boolean validPassword = false;

        while (!validPassword) {
            StringBuilder passwordBuilder = new StringBuilder();
            for (int i = 0; i < digit; i++) {
                int rand = (int) (charPoolString.length() * Math.random());
                passwordBuilder.append(charPoolString.charAt(rand));
            }

            password = passwordBuilder.toString();

            if (isValidPassword(password, hasLowerCase, hasNumbers, hasSpecialChars, hasUpperCase)) {
                validPassword = true;
            }
        }
        return password;
    }
    private boolean passwordValidator (String password, boolean hasLowerCase, boolean hasNumbers, boolean hasSpecialChars, boolean hasUpperCase) {
        boolean hasNumber = password.matches(".*[0-9].*"); //Regex check if the password contains a number
        boolean hasLower = password.matches(".*[a-z].*"); //Regex check if password contains a lowercase letter
        boolean hasCapital = password.matches(".*[A-Z].*"); //Regex check if password contains a capital letter
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*:';_-].*"); //Regex check if password contains a special character

        if (hasLowerCase) {
            return hasLower;
        }
        if (hasNumbers) {
            return hasNumber;
        }
        if (hasUpperCase) {
            return hasCapital;
        }
        if (hasSpecialChars) {
            return hasSpecialChar;
        }
        return false;
    }
    //Getter Method
    public boolean isValidPassword(String password, boolean hasLowerCase, boolean hasUpperCase, boolean hasNumbers, boolean hasSpecialChars) {
        return passwordValidator(password, hasLowerCase, hasUpperCase, hasNumbers, hasSpecialChars);
    }
}