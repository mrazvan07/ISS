package com.example.monitorizareangajati2.repositories;

import com.example.monitorizareangajati2.model.Employee;
import com.example.monitorizareangajati2.model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmployeeORMRepository implements IEmployeeRepository {
    private SessionFactory sessionFactory;

    public EmployeeORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Employee findEmployeeByNameAndPassword(String name, String password) {
        Employee employee = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                employee = session.createQuery("from Employee where name='" + name + "' and password='" + password + "'", Employee.class).setMaxResults(1).uniqueResult();
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("Eroare la find employee by name and password" + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return employee;
    }

    @Override
    public void add(Employee elem) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(elem);
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("Eroare la add employee " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public void delete(Employee elem) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Employee employee = findById(elem.getID());
                System.err.println("Stergem employee " + employee.getID());
                session.delete(employee);
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("Eroare la delete artist " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public void update(Employee elem, Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                //Employee employee = findById(elem.getID());
                //System.err.println("Stergem employee " + employee.getID());
                session.update(elem);
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("Eroare la delete artist " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public Employee findById(Integer id) {
        Employee employee = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                employee = session.createQuery("from Employee where id='"+id+"'", Employee.class).setMaxResults(1).uniqueResult();
                transaction.commit();
            } catch (RuntimeException e){
                System.err.println("Eroare la find employee by id " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return employee;
    }

    @Override
    public Iterable<Employee> findAll() {
        List<Employee> employees = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                employees = session.createQuery("from Employee ",Employee.class).list();
                transaction.commit();

                /*CriteriaBuilder cb = session.getCriteriaBuilder();
                CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
                Root<Employee> rootEntry = cq.from(Employee.class);
                CriteriaQuery<Employee> all = cq.select(rootEntry);
                TypedQuery<Employee> allQuery = session.createQuery(all);
                return allQuery.getResultList();*/
            } catch (RuntimeException e) {
                System.err.println("Eroare la find all employees " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return employees;
    }
}