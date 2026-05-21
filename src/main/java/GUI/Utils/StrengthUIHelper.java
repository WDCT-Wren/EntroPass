package GUI.Utils;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.Password_Generator.Configurator;
import org.Password_Generator.StrengthChecker;

public class StrengthUIHelper {
    private static Configurator getManualConfiguration(String password) {
        boolean hasLower   = password.matches(".*[a-z].*");
        boolean hasUpper   = password.matches(".*[A-Z].*");
        boolean hasNumbers = password.matches(".*[0-9].*");
        boolean hasSymbols = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{}].*");

        return new Configurator(hasNumbers, hasSymbols, hasUpper, hasLower);
    }

    public static ChangeListener<String> manualStrengthListener (ProgressBar numericalStrength, Label descriptiveStrength) {
        return (observable, oldValue, newValue) -> {
            double entropy = StrengthChecker.getSignUpEntropy(StrengthUIHelper.getManualConfiguration(newValue), newValue.length());
            double strength = StrengthChecker.checkManualEntryStrength(entropy);
            String description = StrengthChecker.displayManualEntryStrength(strength);

            numericalStrength.setProgress(strength);
            descriptiveStrength.setText(description);

            if (strength <= 0.25) descriptiveStrength.setStyle("-fx-text-fill: red");
            else descriptiveStrength.setStyle("-fx-text-fill: #a3e635");
        };
    }
}
