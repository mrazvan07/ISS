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

public class TaskORMRepository implements ITaskRepository {
    private SessionFactory sessionFactory;

    public TaskORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Task elem) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(elem);
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("Eroare la add task " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public void delete(Task elem) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Task task = findById(elem.getID());
                System.err.println("Stergem task " + task.getID());
                session.delete(task);
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("Eroare la delete task " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public void update(Task elem, Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                //Task task = findById(elem.getID());
               // System.err.println("Stergem task " + task.getID());
                session.update(elem);
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("Eroare la delete task " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public Task findById(Integer id) {
        Task task = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                task = session.createQuery("from Task where id='"+id+"'", Task.class).setMaxResults(1).uniqueResult();
                transaction.commit();
            } catch (RuntimeException e){
                System.err.println("Eroare la find task by id " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return task;
    }

    @Override
    public Iterable<Task> findAll() {
        List<Task> tasks = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                tasks = session.createQuery("from Task",Task.class).list();
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("Eroare la find all tasks " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return tasks;
    }

    private static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
    }
}
