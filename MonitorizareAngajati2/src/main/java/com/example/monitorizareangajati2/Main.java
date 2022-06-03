package com.example.monitorizareangajati2;

import com.example.monitorizareangajati2.controllers.LoginController;
import com.example.monitorizareangajati2.model.Task;
import com.example.monitorizareangajati2.model.TaskStatus;
import com.example.monitorizareangajati2.repositories.EmployeeORMRepository;
import com.example.monitorizareangajati2.repositories.IEmployeeRepository;
import com.example.monitorizareangajati2.repositories.ITaskRepository;
import com.example.monitorizareangajati2.repositories.TaskORMRepository;
import com.example.monitorizareangajati2.service.SuperService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;

public class Main extends Application {

    private static SessionFactory sessionFactory;

    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Exceptie " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        initialize();
        IEmployeeRepository employeeRepository = new EmployeeORMRepository(sessionFactory);
        ITaskRepository taskRepository = new TaskORMRepository(sessionFactory);
        SuperService superService = new SuperService(employeeRepository,taskRepository);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        LoginController ctrl = fxmlLoader.getController();
        ctrl.setServiceController(superService);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
//        System.out.println("ceva");
//        try {
//            initialize();
//
//            HelloApplication test = new HelloApplication();
//            test.addArtist();
//            /*//  test.getMessages();
//            //test.updateMessage();
//            test.deleteMessage();
//            test.getMessages();*/
//        }catch (Exception e){
//            System.err.println("Exception "+e);
//            e.printStackTrace();
//        }finally {
//            close();
//        }
    }

    private void addArtist() {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Task artist = new Task(1,2, "cc","cv", TaskStatus.FINISHED);
                //Employee artist = new Employee("cc","cc",EmployeeType.REGULAR);
                session.save(artist);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
    }

}