package GUI.Controllers;

import Database.UserRepository;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ResourceBundle;

public class Vault implements Initializable {
    @FXML
    private TableColumn<UserRepository, String> createdDate;

    @FXML
    private TableColumn<UserRepository, String> notes;

    @FXML
    private TableColumn<UserRepository, String> password;

    @FXML
    private TableColumn<UserRepository, String> serviceName;

    @FXML
    private TableColumn<UserRepository, String> userName;

    @FXML
    private TableView<UserRepository> table;

    ObservableList<UserRepository> initialData() {
        UserRepository repo1 = new UserRepository();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceName.setCellValueFactory(new PropertyValueFactory<UserRepository, String>("serviceName"));
        userName.setCellValueFactory(new PropertyValueFactory<UserRepository, String>("userName"));
        password.setCellValueFactory(new PropertyValueFactory<UserRepository, String>("password"));
        notes.setCellValueFactory(new PropertyValueFactory<UserRepository, String>("notes"));
        createdDate.setCellValueFactory(new PropertyValueFactory<UserRepository, String>("createdDate"));

        table.setItems();
    }
}

