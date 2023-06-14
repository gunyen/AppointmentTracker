package com.nguyen.c195.controller;/*
C195
Created by: John Nguyen
Creation Date: 6/12/2023
Creation Time: 12:15 PM
*/

import com.nguyen.c195.DAO.DBConnection;
import com.nguyen.c195.Scheduler;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.nguyen.c195.controller.CustomersController.createCust;

public class CustomerUpdateController implements Initializable {
    public Button custButt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection.openConnection();
        try{
            if(createCust) {
                custButt.setText("Create");
            } else {
                custButt.setText("Update");
            }
        } catch (Exception e) {

        }
        DBConnection.closeConnection();
    }

    public void custCancelButt(ActionEvent actionEvent) throws IOException {

        Scheduler.navigate(actionEvent, "Customers.fxml");
    }

    public void custButt(ActionEvent actionEvent) {
    }
}
