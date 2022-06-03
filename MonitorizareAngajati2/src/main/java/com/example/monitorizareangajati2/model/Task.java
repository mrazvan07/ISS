package com.example.monitorizareangajati2.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "tasks", schema = "public")
public class Task implements Identifiable<Integer> {
    private Integer id;
    private Integer from_emp;
    private Integer to_emp;
    private String deadline;
    private String descriere;
    private TaskStatus status;

    public Task(Integer id,Integer from_emp, Integer to_emp, String deadline,String descriere,TaskStatus status){
        this.id = id;
        this.from_emp = from_emp;
        this.to_emp = to_emp;
        this.deadline = deadline;
        this.descriere = descriere;
        this.status = status;
    }

    public Task(Integer from_emp, Integer to_emp, String deadline,String descriere,TaskStatus status){
        this.id = -1;
        this.from_emp = from_emp;
        this.to_emp = to_emp;
        this.deadline = deadline;
        this.descriere = descriere;
        this.status = status;
    }

    public Task(){
    }


    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public void setID(Integer id) {
        this.id = id;
    }


    public Integer getFrom_emp() {
        return from_emp;
    }

    public void setFrom_emp(Integer from_emp) {
        this.from_emp = from_emp;
    }

    public Integer getTo_emp() {
        return to_emp;
    }

    public void setTo_emp(Integer to_emp) {
        this.to_emp = to_emp;
    }


    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }


    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }


    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task that = (Task) o;
        return getFrom_emp().equals(that.getFrom_emp()) &&
                getTo_emp().equals(that.getTo_emp())&&
                getDescriere().equals(that.getDescriere()) &&
                getStatus().equals(that.getStatus()) &&
                getDeadline().equals(that.getDeadline());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrom_emp(),getTo_emp(),getDescriere(),getDeadline(),getStatus());
    }
}
