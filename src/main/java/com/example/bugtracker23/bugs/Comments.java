package com.example.bugtracker23.bugs;

import javafx.fxml.FXML;

import java.sql.*;

public class Comments {

    @FXML
    private int id;

    @FXML
    private int bugNumber;

    @FXML
    private String author;

    @FXML
    private String content;

    @FXML
    private Timestamp created;

    public Comments(int id, int bug_Number, String author, String content, Timestamp created) {
        this.id = id;
        this.bugNumber = bug_Number;
        this.author = author;
        this.content = content;
        this.created = created;
    }

    public Comments() {
    }

    public Comments(Timestamp created, String author, String content) {
        this.created = created;
        this.author = author;
        this.content = content;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getDescription() {
        return content;
    }

    public void setDescription(String description) {
        this.content = description;
    }

    public String getUser() {
        return author;
    }

    public void setUser(String user) {
        this.author = user;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "created=" + created +
                ", description='" + content + '\'' +
                ", user='" + author + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBugNumber() {
        return bugNumber;
    }

    public void setBugNumber(int bugNumber) {
        this.bugNumber = bugNumber;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
