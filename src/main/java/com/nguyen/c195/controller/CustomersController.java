package com.nguyen.c195.controller;


import com.nguyen.c195.DAO.CustomerDaoImpl;
import com.nguyen.c195.DAO.DBConnection;
import com.nguyen.c195.Scheduler;
import com.nguyen.c195.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    public static boolean createApp;
    public static Customer customerData;

    ObservableList<Customer> Customers = FXCollections.observableArrayList();

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
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, e);
        }

        CustomersTable.setItems(Customers);
    }

    /**
     * <p>Creates scene for AppointmentUpdate.fxml for end user to create an Appointment object with selected Customer</p>
     *
     * @param actionEvent the actionEvent to set
     * @throws IOException
     */
    public void custMakeAppButt(ActionEvent actionEvent) throws IOException {
        if (CustomersTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Customer Information");
            alert.setContentText("Customer must be selected to create an appointment with them");
            alert.showAndWait();
        } else {
            customerData = CustomersTable.getSelectionModel().getSelectedItem();
            createApp = true;
            Scheduler.navigate(actionEvent, "AppointmentUpdate.fxml");
        }
    }

    /**
     * <p>Creates scene for CustomerUpdate.fxml</p>
     *
     * @param actionEvent the actionEvent to set
     * @throws IOException
     */
    public void newCustButt(ActionEvent actionEvent) throws IOException {
        createCust = true;
        Scheduler.navigate(actionEvent, "CustomerUpdate.fxml");
    }

    /**
     * <p>Retrieves information of selected customer from CustomerTable tableview to be able to edit existing data within CustomerUpdate.fxml form</p>
     *
     * @param actionEvent the actionEvent to set
     * @throws Exception
     */
    public void editCustButt(ActionEvent actionEvent) throws Exception {
        DBConnection.openConnection();
        try {
            createCust = false;
            if (CustomersTable.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Customer Information");
                alert.setContentText("Customer must be selected for editing");
                alert.showAndWait();
            } else {
                getCustomerData(CustomersTable.getSelectionModel().getSelectedItem());
                DBConnection.closeConnection();
                Scheduler.navigate(actionEvent, "CustomerUpdate.fxml");
            }
        } catch (Exception e) {

        }
    }

    /**
     * <p>Deletes customer item selected from CustomersTable tableview</p>
     *
     * @param actionEvent the actionEvent to set
     * @throws SQLException
     */
    public void custDeleteButt(ActionEvent actionEvent) throws SQLException {
        DBConnection.openConnection();
        if (CustomersTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Customer");
            alert.setContentText("Must select a customer to delete.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Customer Deletion");
            alert.setContentText("Are you sure you want to delete this customer?\n\nAll appointments associated with this customer will be deleted.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Customer Deletion");
                alert1.setContentText(CustomersTable.getSelectionModel().getSelectedItem().getCustomerName() + "has been deleted");
                CustomerDaoImpl.deleteCustomer(CustomersTable.getSelectionModel().getSelectedItem().getCustomerId());
                CustomersTable.getItems().clear();
                CustomersTable.setItems(CustomerDaoImpl.getAllCustomers());
            }
        }
        DBConnection.closeConnection();
    }

    /**
     * <p>Creates new scene for Appointments.fxml</p>
     *
     * @param actionEvent the actionEvent to set
     * @throws IOException
     */
    public void cancelCustButt(ActionEvent actionEvent) throws IOException {
        Scheduler.navigate(actionEvent, "Appointments.fxml");
    }

    /**
     * <p>Sets value of static Customer variable</p>
     *
     * @param customer the customer to set
     * @return
     */
    public Customer getCustomerData(Customer customer) {
        customerData = customer;
        return customerData;
    }
}
