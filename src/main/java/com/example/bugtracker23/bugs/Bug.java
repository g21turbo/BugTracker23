package com.example.bugtracker23.bugs;

import java.time.LocalDate;

public class Bug {

    private int bugNumber;
    private String title;
    private String description;

    private LocalDate created;

    private LocalDate updated;

    private String status;

    public Bug(){

    }

    public Bug(int bugNumber, String title, String description,
               LocalDate created, LocalDate updated, String status) {
        this.bugNumber = bugNumber;
        this.title = title;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.status = status;
    }

    public int getBugNumber() {
        return bugNumber;
    }

    public void setBugNumber(int bugNumber) {
        this.bugNumber = bugNumber;
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

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
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
                "bugNumber=" + bugNumber +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", status='" + status + '\'' +
                '}';
    }
}