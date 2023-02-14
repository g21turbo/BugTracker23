package com.example.bugtracker23.bugs;

import com.example.bugtracker23.database.DatabaseModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public class BugController {

    ObservableList<Bug> bugData = FXCollections.observableArrayList();
    @FXML
    private TableView<Bug> bugTable;

    @FXML
    private TableColumn<Bug, Integer> bugNumberColumn;

    @FXML
    private TableColumn<Bug, String> titleColumn;

    @FXML
    private TableColumn<Bug, String> descriptionColumn;

    @FXML
    private TableColumn<Bug, LocalDate> createdColumn;

    @FXML
    private TableColumn<Bug, LocalDate> updatedColumn;

    @FXML
    private TableColumn<Bug, String> statusColumn;

    @FXML
    private Button submitBug;



    public void loadBugData() {
        DatabaseHelper database = new DatabaseHelper();
        List<Bug> bugs = database.getBugs();
        bugTable.setItems(FXCollections.observableArrayList(bugs));
    }

    @FXML
    private void initialize() {

        bugNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        createdColumn.setCellValueFactory(new PropertyValueFactory<>("created"));
        updatedColumn.setCellValueFactory(new PropertyValueFactory<>("updated"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

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
