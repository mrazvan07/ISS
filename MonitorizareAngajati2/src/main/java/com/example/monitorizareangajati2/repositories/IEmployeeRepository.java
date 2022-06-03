package com.example.monitorizareangajati2.repositories;

import com.example.monitorizareangajati2.model.Employee;

public interface IEmployeeRepository extends ICrudRepository<Employee,Integer> {
    public Employee findEmployeeByNameAndPassword(String name, String password);
}
