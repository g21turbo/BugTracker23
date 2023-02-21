package com.example.bugtracker23.bugs;


import com.example.bugtracker23.database.DatabaseHelper;
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

    @FXML
    private Button refreshButton;
    @FXML
    private ComboBox<String> comboBox1;

    private ObservableList<String> statusUnits;


    // Loads data of bugs from the database using the DatabaseHelper
    public void loadBugData() {
        DatabaseHelper database = new DatabaseHelper();
        List<Bug> bugs = database.getBugs();
        bugTable.setItems(FXCollections.observableArrayList(bugs));
    }

    @FXML
    private void initialize() {

        // Set the column values for the TableView
        bugNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        createdColumn.setCellValueFactory(new PropertyValueFactory<>("created"));
        updatedColumn.setCellValueFactory(new PropertyValueFactory<>("updated"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusUnits = FXCollections.observableArrayList("Most Recent", "Open", "Closed");
        comboBox1.setItems(statusUnits);
        comboBox1.setPromptText("Filter");

        // Handle the submit button action
        submitBug.setOnAction(event -> {
            try {

                // Load the Submit Bug view FXML file
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/bugtracker23/submit-view.fxml"));
                Parent root = loader.load();

                // Create a new scene with the Submit Bug view and display it in a new stage
                Scene scene = new Scene(root, 432, 280);
                Stage stage = new Stage();
                stage.setTitle("Submit A New Bug");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        // Set an event handler for the refresh button to retrieve updated data from the database
        refreshButton.setOnAction(event -> {
            try {

                // Connect to the database
                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "root")) {

                    // Retrieve the updated data from the database
                    String sql = "SELECT * FROM userlogin.buginfo";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        try (ResultSet resultSet = statement.executeQuery()) {

                            // Create a new observable list to store the updated data
                            ObservableList<Bug> refreshedData = FXCollections.observableArrayList();

                            // Iterate over the result set and add each row to the refreshed data list
                            while (resultSet.next()) {
                                int number = resultSet.getInt("number");
                                String title = resultSet.getString("title");
                                String description = resultSet.getString("description");
                                Timestamp created = resultSet.getTimestamp("created");
                                Timestamp updated = resultSet.getTimestamp("updated");
                                String status = resultSet.getString("status");
                                refreshedData.add(new Bug(number, title, description, created, updated, status));
                            }
                            // Set the refreshed data as the items for the bug TableView
                            bugTable.setItems(refreshedData);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        bugTable.setOnMouseClicked(event -> {
            try {

                // Only proceed if user double clicks and selected bug is not null
                if (event.getClickCount() == 2 && !bugTable.getSelectionModel().isEmpty()) {

                    // Get the selected bug from the table
                    Bug selectedBug = bugTable.getSelectionModel().getSelectedItem();

                    // Load the bug details view FXML file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bugtracker23/details-view.fxml"));
                    Parent root = loader.load();

                    // Get the controller for the bug details view
                    BugDetailsController controller = loader.getController();

                    // Set the selected bug on the controller
                    controller.setBug(selectedBug);

                    // Initialize the bug details view with the selected bug data
                    controller.initialize();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Bug Details");

                    // Interact with current window only
                    stage.initModality(Modality.APPLICATION_MODAL);

                    // Show the bug details view and wait for it to close
                    stage.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}