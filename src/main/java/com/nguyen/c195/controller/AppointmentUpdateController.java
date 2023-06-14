package com.nguyen.c195.controller;/*
C195
Created by: John Nguyen
Creation Date: 6/12/2023
Creation Time: 12:13 PM
*/

import com.nguyen.c195.DAO.ContactDAOImpl;
import com.nguyen.c195.DAO.DBConnection;
import com.nguyen.c195.Scheduler;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.nguyen.c195.controller.AppointmentsController.createApp;

public class AppointmentUpdateController implements Initializable {

    public ComboBox<com.nguyen.c195.model.Contact> appContactComboBox;
    public Button updateButt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection.openConnection();
        try {
            appContactComboBox.setItems(ContactDAOImpl.getAllContacts());
            if(createApp) {
                updateButt.setText("Create");
            } else {
                updateButt.setText("Update");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        DBConnection.closeConnection();
    }

    public void appCancelButt(ActionEvent actionEvent) throws IOException {

        Scheduler.navigate(actionEvent, "Appointments.fxml");
    }

    public void updateButt(ActionEvent actionEvent) {
    }
}
