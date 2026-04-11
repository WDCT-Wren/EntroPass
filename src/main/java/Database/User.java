package Database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This is the data model class used to represent the password details holding data
 * to be retrieved by the {@link UserDAO}
 */
public class User {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty serviceName;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty password;
    private final SimpleStringProperty note;
    private final SimpleStringProperty dateCreated;

    public User(int id, String serviceName, String userName, String password, String notes, String createdDate) {
        this.id = new SimpleIntegerProperty(id);
        this.serviceName = new SimpleStringProperty(serviceName);
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.note = new SimpleStringProperty(notes);
        this.dateCreated = new SimpleStringProperty(createdDate);
    }

    public int getId() {return id.get();}
    public String getServiceName() {return serviceName.get();}
    public String getUserName() {return userName.get();}
    public String getPassword() {return password.get();}
    public String getNotes() {return note.get();}
    public String getCreatedDate() {return dateCreated.get();}
}
