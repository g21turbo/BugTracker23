package com.example.bugtracker23.bugs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BugDetailsController {

    @FXML
    public TableColumn contentColumn;

    @FXML
    public TableColumn authorColumn;

    @FXML
    public TableColumn createdColumn;

    public TableView commentsTable;

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

    }
}
