package GUI.Utils;

import Database.User;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class VaultEntryCell extends ListCell<User> {
    private final Label serviceName = new Label();
    private final Label userName = new Label();
    private final VBox layout = new VBox(2, serviceName, userName);

    public VaultEntryCell() {
        // Remove any .setStyle calls that set colors!
        serviceName.getStyleClass().add("cell-title");
        userName.getStyleClass().add("cell-subtitle");

        // Layout-only settings are fine in Java
        layout.setSpacing(2);
        layout.setPadding(new Insets(8, 12, 8, 12));
        VBox.setMargin(userName, new Insets(0, 0, 0, 12));
    }

    @Override
    protected void updateItem(User entry, boolean empty) {
        super.updateItem(entry, empty);
        if (empty || entry == null) {
            setGraphic(null);
            setText(null);
        } else {
            serviceName.setText(entry.getServiceName());
            userName.setText(entry.getUserName());
            setGraphic(layout);
            setText(null);
        }
    }
}
