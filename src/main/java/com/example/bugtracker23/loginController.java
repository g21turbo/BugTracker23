package com.example.bugtracker23;

import com.example.bugtracker23.database.DatabaseModel;
import com.example.bugtracker23.user.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {

    @FXML
    public MenuItem helpMenuItem;
    @FXML
    private Button loginButton;
    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Hyperlink createAccountLink;


    @FXML
    private void showHelp() {
        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("Help");
        helpAlert.setHeaderText("Help Information");
        helpAlert.setContentText("This is the help information\n" +
                "1. Create an account.\n" +
                "2. Login with username and password.\n" +
                "3. Select 'Forgot Password' to create a new password.\n");
        helpAlert.show();
    }

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Try to get the user from the database using the entered credentials
        try {
            DatabaseModel databaseModel = new DatabaseModel();
            databaseModel.getUser(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {

        createAccountLink.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/bugtracker23/reg-view.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 445, 225);
                Stage stage = new Stage();
                stage.setTitle("New Account Creation");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // If Enter or Tab is pressed after entering the username, text cursor goes to password field
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                passwordField.requestFocus();
            }
        });

        // Pressing the enter key executes the login
        passwordField.setOnAction(event -> loginButton.fire());


        // Close the application when the close menu item is clicked
        closeMenuItem.setOnAction(event -> {
            Platform.exit();
        });

        // Perform action when the login button is clicked
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Try to get the user from the database using the entered credentials
            try {
                DatabaseModel databaseModel = new DatabaseModel();
                User user = databaseModel.getUser(username, password);
                if (user != null) {
                    // Login was successful, show a message and redirect to the next scene
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success");
                    successAlert.setHeaderText("Login successful");
                    successAlert.setContentText("Welcome, " + username);
                    successAlert.showAndWait();

                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/com/example/bugtracker23/bug-view.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root, 600, 400);
                        Stage stage = new Stage();
                        stage.setTitle("Track Your Bugs");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Login was unsuccessful, show an error message
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("Login unsuccessful");
                    errorAlert.setContentText("Invalid username or password");
                    errorAlert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}