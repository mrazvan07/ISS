package com.example.monitorizareangajati2.controllers;

import com.example.monitorizareangajati2.Observer.Observer;
import com.example.monitorizareangajati2.model.Employee;
import com.example.monitorizareangajati2.model.Task;
import com.example.monitorizareangajati2.model.TaskStatus;
import com.example.monitorizareangajati2.service.SuperService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.monitorizareangajati2.model.Constants.DATE_TIME_FORMATTER;

public class EmployeeMainController implements Observer {
    private SuperService superService;
    private Employee currentEmployee;

    ObservableList<Task> allUnfinishedTasks = FXCollections.observableArrayList();
    ObservableList<Task> allFinishedTasks = FXCollections.observableArrayList();


    @FXML
    TextField descriereTextField;
    @FXML
    Button logoutButton;
    @FXML
    Button requestUpdateButton;
    @FXML
    Button takeTaskButton;
    @FXML
    Button finishTaskButton;


    @FXML
    Label nameLabel;

    @FXML
    TableView<Task> unfinishedTasksTableView;

    @FXML
    TableView<Task> finishedTasksTableView;
    @FXML
    TableColumn<Task,String> descriereT1Column;
    @FXML
    TableColumn<Task,String> deadlineT1Column;
    @FXML
    TableColumn<Task,String> statusT1Column;

    @FXML
    TableColumn<Task,String> descriereT2Column;
    @FXML
    TableColumn<Task,String> deadlineT2Column;
    @FXML
    TableColumn<Task,String> statusT2Column;

    @FXML
    public void initialize() {

        descriereT1Column.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        deadlineT1Column.setCellValueFactory( param -> {
            Task task = this.superService.getTaskById(param.getValue().getID()).get();
            ReadOnlyObjectWrapper<String> str = new ReadOnlyObjectWrapper<>();
            if(task != null)
                //str.set(task.getDeadline().format(DATE_TIME_FORMATTER));
                str.set(task.getDeadline());
            return str;
        });
        statusT1Column.setCellValueFactory( param -> {
            Task task = this.superService.getTaskById(param.getValue().getID()).get();
            ReadOnlyObjectWrapper<String> str = new ReadOnlyObjectWrapper<>();
            if(task != null) {
                if (task.getStatus() == TaskStatus.FINISHED)
                    str.set("FINISHED");
                else
                    str.set("ccc");
            }
            return str;
        });
        finishedTasksTableView.setItems(allFinishedTasks);


        descriereT2Column.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        deadlineT2Column.setCellValueFactory( param -> {
            Task task = this.superService.getTaskById(param.getValue().getID()).get();
            ReadOnlyObjectWrapper<String> str = new ReadOnlyObjectWrapper<>();
            if(task != null)
                str.set(task.getDeadline());
            return str;
        });
        statusT2Column.setCellValueFactory( param -> {
            Task task = this.superService.getTaskById(param.getValue().getID()).get();
            ReadOnlyObjectWrapper<String> str = new ReadOnlyObjectWrapper<>();
            if(task != null) {
                if (task.getStatus() == TaskStatus.FINISHED)
                    str.set("FINISHED");
                else if(task.getStatus() == TaskStatus.UNFINISHED)
                    str.set("UNFINISHED");
                else if(task.getStatus() == TaskStatus.ON_HOLD)
                    str.set("ON_HOLD");
                else if(task.getStatus() == TaskStatus.CANCELLED)
                    str.set("CANCELLED");
                else
                    str.set("CCC");
            }
            return str;
        });
        unfinishedTasksTableView.setItems(allUnfinishedTasks);

    }

    public void setServiceController(SuperService superService) {
        this.superService = superService;
    }

    public void setCurrentEmployee(Employee user) {
        this.currentEmployee = user;
    }

    public void afterLoad(SuperService superService, Employee user) {
        this.setServiceController(superService);
        this.setCurrentEmployee(user);
        updateTasks();
        superService.addObserver(this);
    }

    @Override
    public void updateTasks(){
        this.allUnfinishedTasks.clear();
        this.allFinishedTasks.clear();
        Iterable<Task> tasks = this.superService.getAllTasks();
        Iterable<Task> tasksN = StreamSupport.stream(tasks.spliterator(), false)
                .filter(x->x.getTo_emp() == currentEmployee.getID())
                .collect(Collectors.toList());
        this.setTasks(tasksN);
    }

    private void setTasks(Iterable<Task> tasks) {
        tasks.forEach( u -> {
            if(u.getStatus() == TaskStatus.FINISHED)
                this.allFinishedTasks.add(u);
            else
                this.allUnfinishedTasks.add(u);
        });
    }

    public void logout(ActionEvent event) {
    }

    public void onTakeTaskButton(ActionEvent event) {

    }

    public void onRequestUpdateButton(ActionEvent event) {

    }

    public void onFinishTaskButton(ActionEvent event) {
        Task task = unfinishedTasksTableView.getSelectionModel().getSelectedItem();
        superService.updateTask(task,TaskStatus.FINISHED);
        //updateTasks();
    }
}
