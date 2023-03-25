package com.jasur.taskmanagerapp;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskModel {
    private LocalDateTime date;
    private String place;
    private String description;


    private boolean privateTask;

    public TaskModel(LocalDateTime date, String place, String description, boolean privateTask) {
        this.date = date;
        this.place = place;
        this.description = description;
        this.privateTask = privateTask;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPrivateTask() {
        return privateTask;
    }

    public void setPrivateTask(boolean privateTask) {
        this.privateTask = privateTask;
    }

    @Override
    public String toString() {
        return date.toString() + ", " + (description.length() > 40 ? description.substring(0, 40) : description );
    }

}
