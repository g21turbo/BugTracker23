package com.example.bugtracker23.bugs;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;

public class SubmitBugController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TableView<Bug> bugTable;

    private ObservableList<Bug> bugData;

    public void saveBug() {

        // Get the values of th title and description from the text fields
        String title = titleField.getText();
        String description = descriptionArea.getText();

        // Get the time as timestamps for created and updated
        Timestamp created = new Timestamp(System.currentTimeMillis());
        Timestamp updated = new Timestamp(System.currentTimeMillis());

        // Set the initial status of the bug to "Open"
        String status = "Open";

        // Connect to the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "root")) {

            // Get the next bug number by selecting the maximum current number and adding 1
            String nextBugNumberSql = "SELECT MAX(number) + 1 AS next_bug_number FROM userlogin.buginfo";
            int nextBugNumber;
            try (PreparedStatement nextBugNumberStatement = connection.prepareStatement(nextBugNumberSql)) {
                try (ResultSet resultSet = nextBugNumberStatement.executeQuery()) {
                    resultSet.next();
                    nextBugNumber = resultSet.getInt("next_bug_number");
                }
            }

            // Insert the bug information into the database
            String sql = "INSERT INTO userlogin.buginfo (number, title, description, created, updated, status) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, nextBugNumber);
                statement.setString(2, title);
                statement.setString(3, description);
                statement.setObject(4, created);
                statement.setObject(5, updated);
                statement.setString(6, status);
                int result = statement.executeUpdate();

                // If the insertion was successful, show an information dialog and close the stage
                if (result == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Bug submitted successfully!");
                    alert.showAndWait();
                    Stage stage = (Stage) titleField.getScene().getWindow();
                    stage.close();

                }
            }
        } catch (SQLException e) {

            // If an error occurred while inserting the bug, show an error dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while submitting bug.");
            alert.showAndWait();
        }
    }
}

