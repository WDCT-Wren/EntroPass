module org.example.password_generator_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires passay;
    requires java.sql;
    requires bcrypt;
    requires jbcrypt;

    opens org.Password_Generator to javafx.fxml;
    exports org.Password_Generator;
    exports GUI;
    opens GUI to javafx.fxml;
    exports Database;
    opens Database to javafx.fxml;
    exports GUI.Controllers;
    opens GUI.Controllers to javafx.fxml;
    exports Encryption;
    opens Encryption to javafx.fxml;
}