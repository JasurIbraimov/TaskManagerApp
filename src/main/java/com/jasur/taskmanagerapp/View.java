package com.jasur.taskmanagerapp;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class View extends FlowPane {
    private final Controller controller;
    private ListView<TaskModel> taskList;
    private MenuItem changeTaskMenuItem,
            addTaskMenuItem,
            deleteTaskMenuItem,
            privateTasksMenuItem,
            saveMenuItem,
            openMenuItem;

    private Label detailDateText, detailPlaceText, detailDescriptionText;

    public Label getDetailDateText() {
        return detailDateText;
    }

    public Label getDetailPlaceText() {
        return detailPlaceText;
    }

    public Label getDetailDescriptionText() {
        return detailDescriptionText;
    }

    public ListView<TaskModel> getTaskList() {
        return taskList;
    }

    public MenuItem getChangeTaskMenuItem() {
        return changeTaskMenuItem;
    }

    public MenuItem getAddTaskMenuItem() {
        return addTaskMenuItem;
    }

    public MenuItem getDeleteTaskMenuItem() {
        return deleteTaskMenuItem;
    }

    public MenuItem getPrivateTasksMenuItem() {
        return privateTasksMenuItem;
    }

    public MenuItem getSaveMenuItem() {
        return saveMenuItem;
    }

    public MenuItem getOpenMenuItem() {
        return openMenuItem;
    }

    public View() {
        init();
        controller = new Controller(this);
    }
    public void init() {
        MenuBar menuBar = new MenuBar();
        taskList = new ListView<>();

        // Task Menu
        Menu taskMenu = new Menu("Task Menu");
        addTaskMenuItem = new MenuItem("Add Task");

        deleteTaskMenuItem = new MenuItem("Delete Task");
        changeTaskMenuItem = new MenuItem("Change Task");

        privateTasksMenuItem = new MenuItem("See private tasks");
        taskMenu.getItems().addAll(
                addTaskMenuItem,
                deleteTaskMenuItem,
                changeTaskMenuItem,
                privateTasksMenuItem
        );

        // File Menu
        Menu fileMenu = new Menu("File Menu");
        saveMenuItem = new MenuItem("Save Tasks");
        openMenuItem = new MenuItem("Load Tasks");

        fileMenu.getItems().addAll(saveMenuItem, openMenuItem);

        menuBar.getMenus().addAll(taskMenu, fileMenu);
        menuBar.prefWidthProperty().bind(widthProperty());

        // Task List
        taskList.prefWidthProperty().bind(widthProperty());
        taskList.setPrefHeight(350);

        // Detail Box
        Label detailText = new Label("Details about selected Task");
        detailText.prefWidthProperty().bind(widthProperty());
        detailText.setAlignment(Pos.CENTER);
        detailText.setStyle("-fx-font-weight: bold; -fx-font-size: 16px");

        VBox detailBox = new VBox();
        detailBox.setPrefWidth(500);

        detailDateText = new Label("Date: ");
        detailDescriptionText= new Label("Description: ");
        detailPlaceText = new Label("Place: ");

        detailBox.setPrefHeight(195);
        detailBox.setStyle("" +
                "-fx-background-color: white; " +
                "-fx-border-color: #eee; " +
                "-fx-border-width: 2px;" +
                "-fx-border-style: solid;" +
                "-fx-padding: 20px;"
        );
        detailBox.getChildren().addAll(detailDateText, detailDescriptionText, detailPlaceText);

        setOrientation(Orientation.VERTICAL);
        getChildren().addAll(menuBar, taskList, detailText, detailBox);
    }



}
