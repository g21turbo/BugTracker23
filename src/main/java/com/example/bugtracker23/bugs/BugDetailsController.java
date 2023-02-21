package com.example.bugtracker23.bugs;

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
import java.time.LocalDateTime;

public class BugDetailsController {

    @FXML
    private TableView<Comments> commentsTable;

    @FXML
    private TableColumn<Comments, Timestamp> createdColumn;

    @FXML
    private TableColumn<Comments, String> authorColumn;

    @FXML
    private TableColumn<Comments, String> contentColumn;

//    @FXML
//    public TableColumn contentColumn;
//
//    @FXML
//    public TableColumn authorColumn;
//
//    @FXML
//    public TableColumn createdColumn;
//
//    public TableView commentsTable;

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

    private Bug bug;

    public void setBug(Bug bug) {
        this.bug = bug;
    }

    public void initialize() {

        if (bug != null) {
            numberField.setText(String.valueOf(bug.getNumber()));
            titleField.setText(bug.getTitle());
            descriptionArea.setText(bug.getDescription());
            createdField.setText(bug.getCreated().toString());
            updatedField.setText(bug.getUpdated().toString());
            statusBox.setValue(bug.getStatus());
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

        // Set up table columns
        createdColumn.setCellValueFactory(new PropertyValueFactory<>("created"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));

        // Retrieve data from database and add it to the comments list
        ObservableList<Comments> comments = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "root");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT created, author, content FROM userlogin.comments")) {
            while (rs.next()) {
                Timestamp created = rs.getTimestamp("created");
                String author = rs.getString("author");
                String content = rs.getString("content");
                comments.add(new Comments(created, author, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the comments list as the data source for the table view
        commentsTable.setItems(comments);

    }
}
