package com.example.bugtracker23.bugs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class BugDetailsController {
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

    @FXML
    private TableView<Bug> bugTable;

    @FXML
    private void showBugDetails(MouseEvent event) throws IOException {
        Bug selectedBug = bugTable.getSelectionModel().getSelectedItem();
        if (selectedBug != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("details-view.fxml"));
            Parent root = loader.load();

            BugDetailsController controller = loader.getController();
            controller.setBug(selectedBug);
            controller.initialize();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }

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
    }
}
