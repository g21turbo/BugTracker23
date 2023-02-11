package com.example.bugtracker23.database;

import com.example.bugtracker23.user.User;

import java.sql.*;

public class DatabaseModel {

    public User getUser(String username, String password) throws Exception {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String url = "jdbc:mysql://localhost:3306/userlogin";
        String dbUsername = "root";
        String dbPassword = "root";
        String query = "SELECT * FROM userlogin.userinfo WHERE username = ? AND password = ?";

        try {
            // Load and Register the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create the connection
            con = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Create a prepared statement
            preparedStatement = con.prepareStatement(query);

            // Set the parameters
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the results
            User user = null;
            if (resultSet.next()) {
                user = new User(username, password);
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                // You can set other fields as well, if you have them in the User class
            }
            return user;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

