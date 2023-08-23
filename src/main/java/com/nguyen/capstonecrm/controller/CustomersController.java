package com.nguyen.capstonecrm.controller;

import com.nguyen.capstonecrm.DAO.CustomerDaoImpl;
import com.nguyen.capstonecrm.DAO.DBConnection;
import com.nguyen.capstonecrm.Scheduler;
import com.nguyen.capstonecrm.helper.Alerts;
import com.nguyen.capstonecrm.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for Customers.fxml
 */
public class CustomersController implements Initializable {
    public TextField custLookup;
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
    public static boolean createCustomer;
    public static boolean createApp;
    public static Customer customerData;

    ObservableList<Customer> Customers = FXCollections.observableArrayList();

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        try {
            Customers.addAll(CustomerDaoImpl.getAllCustomers());
        } catch (SQLException e) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, e);
        }

        CustomersTable.setItems(Customers);
    }


    /**
     * @param actionEvent
     * @throws IOException
     */
    public void custMakeAppButt(ActionEvent actionEvent) throws IOException {
        if (CustomersTable.getSelectionModel().isEmpty()) {
            Alerts.alertWarning("Missing Customer Information","Customer must be selected to create an appointment with them");
        } else {
            customerData = CustomersTable.getSelectionModel().getSelectedItem();
            createApp = true;
            Scheduler.navigate(actionEvent, "AppointmentUpdate.fxml");
        }
    }


    /**
     * @param actionEvent
     * @throws IOException
     */
    public void newCustButt(ActionEvent actionEvent) throws IOException {
        createCustomer = true;
        Scheduler.navigate(actionEvent, "CustomerUpdate.fxml");
    }


    /**
     * @param actionEvent
     * @throws SQLException
     */
    public void custDeleteButt(ActionEvent actionEvent) throws SQLException, IOException {
        DBConnection.openConnection();
        if (CustomersTable.getSelectionModel().isEmpty()) {
            Alerts.alertWarning("Missing Customer","Must select a customer to delete.");
        } else {
             Optional<ButtonType> result = Alerts.alertConfirmation("Customer Deletion","Are you sure you want to delete this customer?\n\nAll appointments associated with this customer will be deleted.");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Alerts.alertInfo("Customer Deletion",(CustomersTable.getSelectionModel().getSelectedItem().getName() + "has been deleted"));
                CustomerDaoImpl.delete(CustomersTable.getSelectionModel().getSelectedItem().getCustomerID());
                CustomersTable.getItems().clear();
                CustomersTable.setItems(CustomerDaoImpl.getAllCustomers());
            }
        }
        DBConnection.closeConnection();
    }


    /**
     * @param actionEvent
     * @throws IOException
     */
    public void cancelCustButt(ActionEvent actionEvent) throws IOException {
        Scheduler.navigate(actionEvent, "Appointments.fxml");
    }


    /**
     * @param customer
     */
    public void getCustomerData(Customer customer) {
        customerData = customer;
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void custInfoButt(ActionEvent actionEvent) throws IOException {
        DBConnection.openConnection();
        if (CustomersTable.getSelectionModel().isEmpty()) {
            Alerts.alertWarning("Missing Customer Information","Customer must be selected for editing");
        } else {
            getCustomerData(CustomersTable.getSelectionModel().getSelectedItem());
            DBConnection.closeConnection();
            Scheduler.navigate(actionEvent, "CustomerInfo.fxml");
        }
    }

    /**
     * @param actionEvent
     * @throws SQLException
     */
    public void lookupByName(ActionEvent actionEvent) throws SQLException {
        ObservableList<Customer> searchCust = FXCollections.observableArrayList();
        DBConnection.openConnection();
        for(Customer customer : Customers) {
            if(customer.getName().contains(custLookup.getText())) {
                searchCust.add(customer);
            }
            CustomersTable.setItems(searchCust);
        }
        DBConnection.closeConnection();
    }
}
