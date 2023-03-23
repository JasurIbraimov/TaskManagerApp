module com.jasur.taskmanagerapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jasur.taskmanagerapp to javafx.fxml;
    exports com.jasur.taskmanagerapp;
}