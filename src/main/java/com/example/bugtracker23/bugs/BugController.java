package com.example.bugtracker23.bugs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;

public class BugController {


    @FXML
    private Button submitBug;

//    @FXML
//    public void submitBug(ActionEvent event) {
//        // code for handling the submitBug event
//    }

    @FXML
    private void initialize() {

        submitBug.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/bugtracker23/submit-view.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 600, 400);
                Stage stage = new Stage();
                stage.setTitle("Submit A New Bug");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
