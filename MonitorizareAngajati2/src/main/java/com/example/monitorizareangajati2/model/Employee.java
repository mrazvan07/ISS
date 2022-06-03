package com.example.monitorizareangajati2.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employees", schema = "public")
public class Employee implements Identifiable<Integer> {
    private Integer id;
    private String name;
    private String password;
    private EmployeeType type;
    //private EmployeeType type;

//    public Employee(String name, EmployeeType type) {
//        this.name = name;
//        //this.type = type;
//    }

//    public Employee(Integer id, String name, EmployeeType type) {
//        this.setId(id);
//        this.name = name;
//        //this.type = type;
//    }

    public Employee(){}

    public Employee(Integer id, String name, String password, EmployeeType type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
        this.type = type;
    }

    public Employee(String name, String password, EmployeeType type) {
        this.id = -1;
        this.name = name;
        this.password = password;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee that = (Employee) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
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
}
