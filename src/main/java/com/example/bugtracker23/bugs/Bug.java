package com.example.bugtracker23.bugs;

import java.sql.Timestamp;

public class Bug {

    private int number;
    private String title;
    private String description;

    private Timestamp created;

    private Timestamp updated;

    private String status;



    public Bug(){

    }

    public Bug(int number, String title, String description,
               Timestamp created, Timestamp updated, String status) {
        this.number = number;
        this.title = title;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "number=" + number +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", status='" + status + '\'' +
                '}';
    }
}