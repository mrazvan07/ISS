package com.example.monitorizareangajati2.service;

import com.example.monitorizareangajati2.Observer.Observable;
import com.example.monitorizareangajati2.Observer.Observer;
import com.example.monitorizareangajati2.Observer.UpdateType;
import com.example.monitorizareangajati2.model.Employee;
import com.example.monitorizareangajati2.model.Task;
import com.example.monitorizareangajati2.model.TaskStatus;
import com.example.monitorizareangajati2.repositories.IEmployeeRepository;
import com.example.monitorizareangajati2.repositories.ITaskRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SuperService implements Observable {
    private IEmployeeRepository employeeRepository;
    private ITaskRepository taskRepository;
    List<Observer> allObservers = new ArrayList<>();

    public SuperService(IEmployeeRepository employeeRepository, ITaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    public Optional<Employee> login(String username, String password) {
        Employee employee = employeeRepository.findEmployeeByNameAndPassword(username,password);
        if(employee!=null){
                return Optional.of(employee);
        }
        return Optional.empty();
    }

    public Optional<Task> getTaskById(Integer id){
        Task task = taskRepository.findById(id);
        if(task != null)
            return Optional.of(task);
        return Optional.empty();
    }

    public Iterable<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public void updateTask(Task task, TaskStatus finished) {
        task.setStatus(finished);
        taskRepository.update(task, task.getID());
        this.notifyObservers(UpdateType.TASKS);
    }

    @Override
    public void notifyObservers(UpdateType type) {
        for(Observer obs : allObservers){
            if(type == UpdateType.TASKS)
                obs.updateTasks();
        }
    }

    @Override
    public void addObserver(Observer obs) {
        allObservers.add(obs);
    }
}
