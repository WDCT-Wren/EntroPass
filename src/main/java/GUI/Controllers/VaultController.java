package GUI.Controllers;

import Database.UserCRUD.User;
import Database.UserCRUD.UserDAO;
import Database.UserCRUD.UserOperations;
import Encryption.AES;
import GUI.Utils.SceneUtils;
import GUI.Utils.StrengthUIHelper;
import GUI.Utils.VaultEntryCell;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VaultController implements Initializable {
    @FXML
    private TextField searchField;

    @FXML
    private Label itemAmountLabel;

    @FXML
    private ProgressBar strengthIndicator;

    @FXML
    private Button copyPassword;

    @FXML
    private Button copyUsername;

    @FXML
    private Button editEntryButton;

    @FXML
    private Label createdDate;

    @FXML
    private TextArea notes;

    @FXML
    private HBox buttonContainer;

    @FXML
    private VBox strengthContainer;

    @FXML
    private PasswordField password;

    @FXML
    private TextField serviceName;

    @FXML
    private TextField userName;

    @FXML
    private Label passwordStrength;

    @FXML
    private ListView<User> userRepoList;
    private final UserDAO userDAO = new UserDAO();
    private boolean isEditMode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate the listview with all the assets initially
        populateList();
        notes.setWrapText(true);

        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    if (searchField.getText().isEmpty()) {
                        populateList();
                    }
                    else {
                        populateSearchedList();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ChangeListener<String> manualStrengthListener = StrengthUIHelper.manualStrengthListener(strengthIndicator, passwordStrength);
        password.textProperty().addListener(manualStrengthListener);

        isEditMode = false;
        strengthContainer.setVisible(false);
        strengthContainer.setManaged(false);
        buttonContainer.setVisible(false);
        buttonContainer.setManaged(false);

        userRepoList.getSelectionModel().selectFirst();

        itemAmountLabel.setText(String.valueOf(userDAO.getRowCount()));
    }

    private void populateList() {
        userRepoList.getItems().clear();
        userRepoList.setCellFactory(lv -> new VaultEntryCell());
        userRepoList.getItems().addAll(userDAO.loadRepoData());

        userRepoList.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        populateDetail(newValue);
                    }
                })
        );
    }

    private void populateSearchedList() throws SQLException {
        userRepoList.getItems().clear();
        String searched = searchField.getText();
        userRepoList.setCellFactory(lv -> new VaultEntryCell());
        userRepoList.getItems().addAll(userDAO.loadRepoData(searched));

        userRepoList.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        populateDetail(newValue);
                    }
                })
        );
    }

    private void populateDetail(User user) {
        try {
            serviceName.setText(AES.decrypt(user.getServiceName()));
            userName.setText(AES.decrypt(user.getUserName()));
            password.setText(AES.decrypt(user.getPassword()));
        } catch (RuntimeException e) {
            password.setText("[Legacy Password - Please re-enter password]");
            password.setStyle("-fx-text-fill: #FF6B6B;");
        }

        notes.setText(AES.decrypt(user.getNotes()));
        createdDate.setText(user.getCreatedDate());
    }

    @FXML
    public void switchToMenuScene(ActionEvent event) throws IOException {
        String fxmlFile = "StartingMenu.fxml";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SceneUtils.getScene(stage, fxmlFile);
    }

    @FXML
    private void copyPassword() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(password.getText());
        clipboard.setContent(content);
    }

    @FXML
    private void copyUsername() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(userName.getText());
        clipboard.setContent(content);
    }

    private void deleteEntry() throws SQLException, IOException {
        User user = userRepoList.getSelectionModel().getSelectedItem();
        int id = user.getId();
        UserOperations.deletePassword(id);

        populateDetail(user);
        populateList();
        itemAmountLabel.setText(String.valueOf(userDAO.getRowCount()));
        userRepoList.getSelectionModel().selectFirst();
    }

    @FXML
    public void deleteConfirmation() throws IOException {
        SceneUtils.showWindow("ConfirmationWindow.fxml",
                "Delete " + serviceName.getText() + " entry?",
                "Are you sure? All values associated with this entry will be deleted and cannot be undone.",
                "Delete Entry",
                () -> {
                    try {
                        deleteEntry();

                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @FXML
    public void toggleEditEntry() {
        isEditMode = !isEditMode;
        entryEditMode();
    }

    private void entryEditMode() {
        // Sets the editability of the text fields
        serviceName.setEditable(isEditMode);
        serviceName.requestFocus();
        userName.setEditable(isEditMode);
        password.setEditable(isEditMode);
        notes.setEditable(isEditMode);

        // Hides/shows strength indicator
        strengthContainer.setVisible(isEditMode);
        strengthContainer.setManaged(isEditMode);

        // Save and Cancel buttons are hidden/shown
        buttonContainer.setVisible(isEditMode);
        buttonContainer.setManaged(isEditMode);

        // Edit button is hidden while in editing mode
        editEntryButton.setVisible(!isEditMode);
    }

    @FXML
    void switchToPasswordBuilder(ActionEvent event) throws IOException {
        String fxmlFile = "PasswordBuilder.fxml";

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SceneUtils.getScene(stage, fxmlFile);
    }

//    private boolean isEditValid() {
//        boolean isServiceNameInvalid = serviceName.getText().isEmpty();
//        boolean isUserNameInvalid = userName.getText().isEmpty();
//        boolean isPasswordValid;
//
//    }

    private void saveEdits(int id) {
        UserOperations user = new UserOperations(
                getServiceName(),
                getUsername(),
                getPassword(),
                getNotes());

        // Saves the new changes to the database
        user.updatePassword(id);
        itemAmountLabel.setText(String.valueOf(userDAO.getRowCount()));
        populateDetail(userRepoList.getSelectionModel().getSelectedItem());
        populateList();
        userRepoList.getSelectionModel().selectFirst();
        toggleEditEntry();
    }

    @FXML
    public void saveEditsConfirmation() throws IOException{
        int id = userRepoList.getSelectionModel().getSelectedItem().getId();

        SceneUtils.showWindow("ConfirmationWindow.fxml",
                "Update " + serviceName.getText() + " entry?",
                "Are you sure? All values associated with this entry will be changed and cannot be undone.",
                "Save Edits",
                () -> {
                    saveEdits(id);
                });
    }

    @FXML
    public void cancelEdits() {
        populateDetail(userRepoList.getSelectionModel().getSelectedItem());
        toggleEditEntry();
    }

    //Getter Methods with all fields encrypted
    private String getUsername() {return AES.encrypt(userName.getText());}
    private String getServiceName() {return AES.encrypt(serviceName.getText());}
    private String getNotes() {
        if (notes.getText().isEmpty()) return AES.encrypt("");
        return AES.encrypt(notes.getText());
    }
    private String getPassword() {
        return AES.encrypt(password.getText());
    }
}