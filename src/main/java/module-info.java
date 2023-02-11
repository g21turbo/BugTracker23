module com.example.bugtracker23 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires spring.beans;
    requires spring.context;


    opens com.example.bugtracker23 to javafx.fxml;
    exports com.example.bugtracker23;
}