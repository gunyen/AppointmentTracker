package com.nguyen.c195.controller;/*
C195
Created by: John Nguyen
Creation Date: 6/12/2023
Creation Time: 12:14 PM
*/


import com.nguyen.c195.DAO.CustomerDaoImpl;
import com.nguyen.c195.Scheduler;
import com.nguyen.c195.model.Customer;
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

public class CustomersController implements Initializable {
    @FXML
    private TableView<Customer> CustomersTable;
    @FXML
    private TableColumn<?, ?> customerIDCol;
    @FXML
    private TableColumn<?, ?> customerNameCol;
    @FXML
    private TableColumn<?, ?> addressCol;
    @FXML
    private TableColumn<?, ?> postalCodeCol;
    @FXML
    private TableColumn<?, ?> phoneCol;
    @FXML
    private TableColumn<?, ?> divisionIDCol;

    public static boolean createCust;

    ObservableList<Customer> Customers= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        try {
            Customers.addAll(CustomerDaoImpl.getAllCustomers());
        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
        }

        CustomersTable.setItems(Customers);

    }


    public void editCustButt(ActionEvent actionEvent) throws IOException {
        createCust = false;
        if(CustomersTable.getSelectionModel().isEmpty()){

        }else {
            Scheduler.navigate(actionEvent, "CustomerUpdate.fxml");
        }
    }

    public void newCustButt(ActionEvent actionEvent) throws IOException {
        createCust = true;
        Scheduler.navigate(actionEvent, "CustomerUpdate.fxml");
    }

    public void cancelCustButt(ActionEvent actionEvent) throws IOException {
        Scheduler.navigate(actionEvent, "Appointments.fxml");
    }
}
