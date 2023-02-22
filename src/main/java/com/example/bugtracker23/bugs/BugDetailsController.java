package com.example.bugtracker23.bugs;

import com.example.bugtracker23.comments.CommentForBugs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class BugDetailsController {

    @FXML
    private TableView<CommentForBugs> commentsTable;

    @FXML
    private TableColumn<CommentForBugs, Timestamp> createdColumn;

    @FXML
    private TableColumn<CommentForBugs, String> authorColumn;

    @FXML
    private TableColumn<CommentForBugs, String> contentColumn;

    @FXML
    private ChoiceBox<String> statusChoiceBox;

    @FXML
    private Button updateBugButton;

    @FXML
    public Button createNewCommentButton;

    @FXML
    private TextField numberField;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField createdField;

    @FXML
    private TextField updatedField;

    @FXML
    private ChoiceBox<String> statusBox;

    @FXML
    private Button refreshCommentButton;

    private Bug bug;

    public void setBug(Bug bug) {
        this.bug = bug;
    }

    public void initialize() {

        // Set up the status choice box
        ObservableList<String> statusOptions = FXCollections.observableArrayList(
                "Open", "In Progress", "Closed"
        );
        statusChoiceBox.setItems(statusOptions);

        updateBugButton.setOnAction(event -> {
            // Get the updated bug information
            String title = titleField.getText();
            String description = descriptionArea.getText();
            String status = statusChoiceBox.getValue();

            // Update the bug in the database
            String updateBugSql = "UPDATE buginfo SET title = ?, description = ?, status = ? WHERE number = ?";
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "root");
                 PreparedStatement pstmt = conn.prepareStatement(updateBugSql)) {
                pstmt.setString(1, title);
                pstmt.setString(2, description);
                pstmt.setString(3, status);
                pstmt.setInt(4, bug.getNumber());
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Bug Updated");
                    alert.setHeaderText(null);
                    alert.setContentText("Bug updated successfully.");
                    Stage stage = (Stage) statusChoiceBox.getScene().getWindow();
                    alert.showAndWait();
                    stage.close();
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update nug.");
                alert.showAndWait();
            }
        });

        if (bug != null) {
            numberField.setText(String.valueOf(bug.getNumber()));
            titleField.setText(bug.getTitle());
            descriptionArea.setText(bug.getDescription());
            createdField.setText(bug.getCreated().toString());
            updatedField.setText(bug.getUpdated().toString());
            statusChoiceBox.setValue(bug.getStatus());

            // Retrieve comments for the current bug from the database and add them to the comments list
            ObservableList<CommentForBugs> comments = FXCollections.observableArrayList();
            String selectCommentsSql = "SELECT c.created, c.author, c.content FROM userlogin.comments c WHERE c.bugNumber = ?";
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "root");
                 PreparedStatement pstmt = conn.prepareStatement(selectCommentsSql)) {
                pstmt.setInt(1, bug.getNumber());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Timestamp created = Timestamp.valueOf(rs.getTimestamp("created").toLocalDateTime());
                    String author = rs.getString("author");
                    String content = rs.getString("content");
                    comments.add(new CommentForBugs(created, author, content));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Set the comments list as the data source for the table view
            commentsTable.setItems(comments);
            createdColumn.setCellValueFactory(new PropertyValueFactory<>("created"));
            authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
            contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        }

        createNewCommentButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/bugtracker23/submitComment-view.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 450, 400);
                Stage stage = new Stage();
                stage.setTitle("New Comment");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        refreshCommentButton.setOnAction(event -> {
            try {
                // Connect to the database
                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "root")) {
                    // Create a new observable list to store the updated data
                    ObservableList<CommentForBugs> refreshedData = FXCollections.observableArrayList();

                    // Retrieve the updated data from the database
                    String selectCommentsSql = "SELECT c.created, c.author, c.content FROM userlogin.comments c WHERE c.bugNumber = ?";
                    try (PreparedStatement statement = connection.prepareStatement(selectCommentsSql)) {
                        statement.setInt(1, bug.getNumber());
                        ResultSet rs = statement.executeQuery();
                        while (rs.next()) {
                            Timestamp created = Timestamp.valueOf(rs.getTimestamp("created").toLocalDateTime());
                            String author = rs.getString("author");
                            String content = rs.getString("content");
                            refreshedData.add(new CommentForBugs(created, author, content));
                        }
                    }
                    // Update the table view with the refreshed data
                    commentsTable.setItems(refreshedData);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}








