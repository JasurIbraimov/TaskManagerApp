package com.jasur.taskmanagerapp;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class TaskModelCellFactory implements Callback<ListView<TaskModel>, ListCell<TaskModel>> {
    @Override
    public ListCell<TaskModel> call(ListView<TaskModel> param) {
        return new ListCell<>(){
            @Override
            public void updateItem(TaskModel task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task.isPrivateTask()) {
                    setText(null);
                } else {
                    setText(task.toString());
                }
            }
        };
    }
}