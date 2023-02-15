package com.example.bugtracker23.register;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    // Define a method to save user data
    public void saveUser() {

        // Get the username and password input from text fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Connect to the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "root")) {

            // Prepare an SQL INSERT statement to add user to the database
            String query = "INSERT INTO userlogin.userinfo (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            int result = statement.executeUpdate();
            if (result == 1) {

                // Show an alert message if the account is created successfully
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Account created successfully!");
                alert.showAndWait();

                // Close the current window after account creation
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException e) {

            // Show an error message if an exception occurs while creating the account
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while creating the account.");
            alert.showAndWait();
        }
    }
}

