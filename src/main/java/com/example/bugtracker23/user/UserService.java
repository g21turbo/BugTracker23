package com.example.bugtracker23.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UserService {

    private final DataSource dataSource;

    @Autowired
    public UserService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean authentication(String username, String password) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
