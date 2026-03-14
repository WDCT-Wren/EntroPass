package Database;

import javafx.beans.property.SimpleStringProperty;

//Data Model
public class User {
    // Source - https://stackoverflow.com/q/34823563
    // Posted by Joseph, I modified it to fit my project.
    // Retrieved 2026-03-11, License - CC BY-SA 3.0
    private final SimpleStringProperty serviceName;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty password;
    private final SimpleStringProperty note;
    private final SimpleStringProperty dateCreated;

    public User(String serviceName, String userName, String password, String notes, String createdDate) {
        this.serviceName = new SimpleStringProperty(serviceName);
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.note = new SimpleStringProperty(notes);
        this.dateCreated = new SimpleStringProperty(createdDate);
    }

    public String getServiceName() {return serviceName.get();}
    public String getUserName() {return userName.get();}
    public String getPassword() {return password.get();}
    public String getNotes() {return note.get();}
    public String getCreatedDate() {return dateCreated.get();}
}
