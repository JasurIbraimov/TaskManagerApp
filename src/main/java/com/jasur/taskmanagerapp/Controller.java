package com.jasur.taskmanagerapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Controller {
    private final View view;
    private int selectedTaskID;
    private final ObservableList<TaskModel> tasks;
    public Controller(View view) {
        tasks = FXCollections.observableArrayList();
        this.view = view;

        view.getPrivateTasksMenuItem().setOnAction(actionEvent -> {
            showPrivateTasks();
        });
        view.getTaskList().setItems(tasks);
        view.getTaskList().setCellFactory(new TaskModelCellFactory());
        view.getAddTaskMenuItem().setOnAction(actionEvent -> {
            showPopup(null, "Create Task");
        });
        view.getChangeTaskMenuItem().setOnAction(actionEvent -> {
            TaskModel task = view.getTaskList().getSelectionModel().getSelectedItem();
            selectedTaskID = view.getTaskList().getSelectionModel().getSelectedIndex();
            if (selectedTaskID != -1) {
                showPopup(task, "Change Task");
            } else {
                showAlertMessage(Alert.AlertType.ERROR, "Error!", "Task not selected!",
                        "Please select task to change!");
            }
        });
        view.getDeleteTaskMenuItem().setOnAction(actionEvent -> {
            selectedTaskID = view.getTaskList().getSelectionModel().getSelectedIndex();
            if (selectedTaskID != -1) {
                deleteTask(selectedTaskID);
            } else {
                showAlertMessage(Alert.AlertType.ERROR, "Error!", "Task not selected!",
                        "Please select task to delete!");
            }
        });
        view.getTaskList().setOnMouseClicked(mouseEvent -> {
            TaskModel task = view.getTaskList().getSelectionModel().getSelectedItem();
            if (task != null) {
                view.getDetailDateText().setText("Date: " + task.getDate().toString());
                view.getDetailDescriptionText().setText("Description: " + task.getDescription());
                view.getDetailPlaceText().setText("Place: " + task.getPlace());
            }

        });

    }
    public void addTask(LocalDateTime dateTime, String place, String description, boolean privateTask) {
        tasks.add(new TaskModel(dateTime, place, description, privateTask));
        view.getTaskList().refresh();
    }

    public void changeTask(int id, LocalDateTime dateTime, String place, String description, boolean privateTask ) {
        TaskModel taskModel = tasks.get(id);
        taskModel.setDate(dateTime);
        taskModel.setPlace(place);
        taskModel.setDescription(description);
        taskModel.setPrivateTask(privateTask);
        tasks.set(id, taskModel);
        view.getTaskList().refresh();
    }

    public void deleteTask(int id) {
        tasks.remove(id);
        view.getTaskList().refresh();
    }
    public void showPopup(TaskModel task, String title) {
        final int POPUP_WIDTH = 500;
        final int POPUP_HEIGHT = 250;
        Stage stage = new Stage();
        FlowPane root = new FlowPane();

        Label dateText = new Label("Date: ");
        dateText.setPrefWidth(POPUP_WIDTH/2f);
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(POPUP_WIDTH/4f);
        if (task != null) datePicker.setValue(task.getDate().toLocalDate());
        ArrayList<String> hours = new ArrayList<>();
        for(int i = 0; i <= 24; i++) hours.add( i < 10 ? "0" + i : "" + i);
        ArrayList<String> minutes = new ArrayList<>();
        for(int i = 0; i <= 60; i++) minutes.add( i < 10 ? "0" + i : "" + i);
        ComboBox<String> hoursComboBox = new ComboBox<>();
        hoursComboBox.getItems().addAll(hours);
        ComboBox<String> minutesComboBox = new ComboBox<>();
        minutesComboBox.getItems().addAll(minutes);
        hoursComboBox.setPrefWidth(POPUP_WIDTH/8f);
        hoursComboBox.setPrefWidth(POPUP_WIDTH/8f);
        HBox dateBox = new HBox(dateText, datePicker, hoursComboBox, minutesComboBox);

        Label placeText = new Label("Place: ");
        TextField placeTextField = new TextField();
        placeText.setPrefWidth(POPUP_WIDTH/2f);
        placeTextField.setPrefWidth(POPUP_WIDTH/2f);
        if (task != null) placeTextField.setText(task.getPlace());
        HBox placeBox = new HBox(placeText, placeTextField);

        Label descriptionText = new Label("Description: ");
        TextArea descriptionTextArea = new TextArea();
        descriptionText.setPrefWidth(POPUP_WIDTH/2f);
        descriptionTextArea.setPrefWidth(POPUP_WIDTH/2f);
        descriptionTextArea.setPrefHeight(50);
        if (task != null) descriptionTextArea.setText(task.getDescription());
        HBox descriptionBox = new HBox(descriptionText, descriptionTextArea);

        Label privateText = new Label("Private: ");
        CheckBox privateCheckBox = new CheckBox();
        if (task != null) privateCheckBox.setSelected(task.isPrivateTask());
        privateText.setPrefWidth(POPUP_WIDTH/2f);
        HBox privateBox = new HBox(privateText, privateCheckBox);

        Button submitBtn = new Button("OK");
        submitBtn.setOnAction(actionEvent -> {
            LocalDate date = datePicker.getValue();
            if(date == null) {
                showAlertMessage(Alert.AlertType.ERROR, "Add Task Error", "Not provided Date!",
                        "Please provide a date to add Task!");
                return;
            }
            String hour = hoursComboBox.getValue();
            if (hour == null) {
                showAlertMessage(Alert.AlertType.ERROR, "Add Task Error", "Not provided Hour for date!",
                        "Please provide an hour to add Task!");
                return;
            }
            String minute = minutesComboBox.getValue();
            if (minute == null) {
                showAlertMessage(Alert.AlertType.ERROR, "Add Task Error", "Not provided Minutes for date!",
                        "Please provide Minutes to add Task!");
                return;
            }
            String place = placeTextField.getText();
            if (place.isEmpty()) {
                showAlertMessage(Alert.AlertType.ERROR, "Add Task Error", "Not provided Place for Task!",
                        "Please provide a place to add Task!");
                return;
            }
            String description = descriptionTextArea.getText();
            if (description.isEmpty()) {
                showAlertMessage(Alert.AlertType.ERROR, "Add Task Error", "Not provided Description for Task!",
                        "Please provide a description to add Task!");
                return;
            }
            LocalDateTime dateTime = date.atTime(Integer.parseInt(hour), Integer.parseInt(minute));
            boolean isPrivate = privateCheckBox.isSelected();
            if (task != null) {
                changeTask(selectedTaskID, dateTime, place, description, isPrivate);
            } else {
                addTask(dateTime, place, description, isPrivate);
            }
            stage.close();
        });

        submitBtn.setPrefWidth(POPUP_WIDTH/2f);
        root.setOrientation(Orientation.VERTICAL);
        root.setVgap(10);
        root.getChildren().addAll(dateBox, placeBox, descriptionBox, privateBox, submitBtn);

        Scene scene = new Scene(root, POPUP_WIDTH, POPUP_HEIGHT);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void showPrivateTasks() {
        final int POPUP_WIDTH = 500;
        final int POPUP_HEIGHT = 250;
        Stage stage = new Stage();
        FlowPane root = new FlowPane();
        ListView<TaskModel> privateListView = new ListView<>();
        privateListView.setItems(tasks.filtered(termin -> !termin.isPrivateTask()));
        privateListView.prefWidthProperty().bind(root.widthProperty());
        privateListView.prefHeightProperty().bind(root.heightProperty());
        root.getChildren().addAll(privateListView);
        Scene scene = new Scene(root, POPUP_WIDTH, POPUP_HEIGHT);
        stage.setTitle("Private Tasks");
        stage.setScene(scene);
        stage.show();
    }

    public void showAlertMessage(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType buttonTypeNo = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeNo);
        alert.show();
    }
}
