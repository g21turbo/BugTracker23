package com.example.bugtracker23.bugs;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private Connection connection;

    public DatabaseHelper() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "root");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Bug> getBugs() {
        List<Bug> bugs = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM buginfo");
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
