package com.example.bugtracker23.database;

import com.example.bugtracker23.bugs.Bug;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a connection to the MySQL database and allows interaction with it.
 */


public class DatabaseHelper {

    // The connection to the database
    private Connection connection;


    /**
     * Constructor for DatabaseHelper.
     * Creates a new connection to the MySQL database.
     */
    public DatabaseHelper() {
        try {

            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "root");
        } catch (ClassNotFoundException | SQLException e) {

            // Print the stack trace if there's an error loading the JDBC driver or connecting to the database
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of all the bugs in the database.
     * @return a List of Bug objects
     */
    public List<Bug> getBugs() {
        List<Bug> bugs = new ArrayList<>();
        try {

            // Create a new Statement object to execute the SQL query
            Statement statement = connection.createStatement();

            // Execute the SQL query and get the results as a ResultSet object
            ResultSet resultSet = statement.executeQuery("SELECT * FROM buginfo");

            // Iterate over each row in the ResultSet and create a new Bug object for each row
            while (resultSet.next()) {
                int bugNumber = resultSet.getInt("number");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Timestamp created = Timestamp.valueOf(resultSet.getString("created"));
                Timestamp updated = Timestamp.valueOf(resultSet.getString("updated"));
                String status = resultSet.getString("status");
                bugs.add(new Bug(bugNumber, title, description, created, updated, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bugs;
    }
}