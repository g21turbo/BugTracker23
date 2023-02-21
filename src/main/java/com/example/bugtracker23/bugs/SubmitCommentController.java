package com.example.bugtracker23.bugs;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;

public class SubmitCommentController {

    @FXML
    public TextArea commentArea;

    @FXML
    public TextField authorField;

    @FXML
    public TextField bugNumberField;

    @FXML
    public void submitComment() {
        String bugNumber = bugNumberField.getText();
        String author = authorField.getText();
        String content = commentArea.getText();
        LocalDateTime created = LocalDateTime.now();

        String insertCommentSql = "INSERT INTO userlogin.comments (id, bugNumber, author, content, created) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "root");
             PreparedStatement pstmt = conn.prepareStatement(insertCommentSql)) {
            pstmt.setInt(1, getNextCommentId(conn)); // get the next comment ID
            pstmt.setString(2, bugNumber);
            pstmt.setString(3, author);
            pstmt.setString(4, content);
            pstmt.setObject(5, created);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Comment Submitted");
                alert.setHeaderText(null);
                alert.setContentText("Comment submitted successfully.");

                Stage stage = (Stage) bugNumberField.getScene().getWindow();
                alert.showAndWait();
                stage.close();
            } else {
                throw new SQLException("Failed to submit comment.");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to submit comment.");

            alert.showAndWait();
        }
    }

    private int getNextCommentId(Connection conn) throws SQLException {
        String nextCommentIdSql = "SELECT MAX(id) + 1 AS next_comment_id FROM userlogin.comments";
        try (PreparedStatement pstmt = conn.prepareStatement(nextCommentIdSql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("next_comment_id");
            } else {
                return 1;
            }
        }
    }
}
