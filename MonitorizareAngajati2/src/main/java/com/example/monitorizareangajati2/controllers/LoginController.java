package com.example.monitorizareangajati2.controllers;

import com.example.monitorizareangajati2.model.Employee;
import com.example.monitorizareangajati2.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginController {
    SuperService superService;

    @FXML
    Label usernameLabel;
    @FXML
    Label passwordLabel;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Button loginButton;


    public void setServiceController(SuperService serviceController) {
        this.superService = serviceController;
    }

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if(username.equals("")){
            return;
        }
        try {
            Optional<Employee> found = superService.login(username, password);
            if (found.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("This user doesn't exist!\n");
                alert.showAndWait();
                return;
            }
            Node source = (Node) event.getSource();
            Stage current = (Stage) source.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/employee-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 900, 600);
            current.setTitle("Pixel " + username);
            current.setScene(scene);
            EmployeeMainController ctrl = fxmlLoader.getController();
            ctrl.afterLoad(superService,found.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
