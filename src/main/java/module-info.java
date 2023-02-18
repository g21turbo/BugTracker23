module com.example.bugtracker {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires spring.beans;
    requires spring.context;


    opens com.example.bugtracker23 to javafx.fxml;
    opens com.example.bugtracker23.register to javafx.fxml;
    opens com.example.bugtracker23.bugs;
    opens com.example.bugtracker23.database;

    exports com.example.bugtracker23;
    exports com.example.bugtracker23.register;
    exports com.example.bugtracker23.bugs;
    exports com.example.bugtracker23.database;



}