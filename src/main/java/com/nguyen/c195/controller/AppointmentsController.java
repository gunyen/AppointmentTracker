package com.nguyen.c195.controller;/*
C195
Created by: John Nguyen
Creation Date: 6/8/2023
Creation Time: 3:08 PM
*/

import com.nguyen.c195.DAO.AppointmentDaoImpl;
import com.nguyen.c195.Scheduler;
import com.nguyen.c195.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentsController implements Initializable {

    @FXML
    private TableView<Appointment> AppointmentTable;
    @FXML
    private TableColumn<?, ?> AppID;
    @FXML
    private TableColumn<?, ?> Title;
    @FXML
    private TableColumn<?, ?> Description;
    @FXML
    private TableColumn<?, ?> Location;
    @FXML
    private TableColumn<?, ?> Contact;
    @FXML
    private TableColumn<?, ?> Type;
    @FXML
    private TableColumn<?, ?> Start;
    @FXML
    private TableColumn<?, ?> End;
    @FXML
    private TableColumn<?, ?> CustomerID;
    @FXML
    private TableColumn<?, ?> UserID;

    public static boolean createApp;

    ObservableList<Appointment> Appointment= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        Contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        Start.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        End.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        UserID.setCellValueFactory(new PropertyValueFactory<>("userId"));

        try {
            Appointment.addAll(AppointmentDaoImpl.getAllAppointments());
        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
        }

        AppointmentTable.setItems(Appointment);
    }

    public void customerButton(ActionEvent actionEvent) throws IOException {
        Scheduler.navigate(actionEvent, "Customers.fxml");
    }

    public void appCreateButt(ActionEvent actionEvent) throws IOException {
        createApp = true;
        Scheduler.navigate(actionEvent, "AppointmentUpdate.fxml");
    }

    public void appEditButt(ActionEvent actionEvent) throws IOException {
        createApp = false;
        if(AppointmentTable.getSelectionModel().isEmpty()){

        }else {
            Scheduler.navigate(actionEvent, "AppointmentUpdate.fxml");
        }
    }

    public void logoutButt(ActionEvent actionEvent) throws IOException {

        Scheduler.navigate(actionEvent, "Login.fxml");
    }
}
