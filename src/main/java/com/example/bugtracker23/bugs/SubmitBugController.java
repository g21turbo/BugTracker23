package com.example.bugtracker23.bugs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;

public class SubmitBugController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;



    private int getNextBugNumber() {
        int nextBugNumber = 1;
        // You can fetch the next available bug number from the database or any other data source here.
        return nextBugNumber;
    }


    public void saveBug() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        Timestamp created = new Timestamp(System.currentTimeMillis());
        Timestamp updated = new Timestamp(System.currentTimeMillis());
        String status = "Open";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "root")) {
            String sql = "INSERT INTO userlogin.buginfo (number, title, description, created, updated, status) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int bugNumber = getNextBugNumber();
                statement.setInt(1, bugNumber);
                statement.setString(2, title);
                statement.setString(3, description);
                statement.setObject(4, created);
                statement.setObject(5, updated);
                statement.setString(6, status);
                statement.executeUpdate();
                Stage stage = (Stage) titleField.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while submitting bug.");
            alert.showAndWait();
        }
    }
}
