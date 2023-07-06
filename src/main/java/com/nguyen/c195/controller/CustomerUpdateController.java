package com.nguyen.c195.controller;/*
C195
Created by: John Nguyen
Creation Date: 6/12/2023
Creation Time: 12:15 PM
*/

import com.nguyen.c195.DAO.CountryDaoImpl;
import com.nguyen.c195.DAO.CustomerDaoImpl;
import com.nguyen.c195.DAO.DBConnection;
import com.nguyen.c195.DAO.DivisionDaoImpl;
import com.nguyen.c195.Scheduler;
import com.nguyen.c195.helper.CountryDivOrg;
import com.nguyen.c195.model.Country;
import com.nguyen.c195.model.Division;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.nguyen.c195.controller.CustomersController.createCust;
import static com.nguyen.c195.controller.CustomersController.customerData;

/**
 * Controller for CustomerUpdate.fxml
 */
public class CustomerUpdateController implements Initializable {
    public Button custButt;
    public TextField custNameField;
    public TextField custAddressField;
    public TextField custPostalField;
    public TextField custPhoneField;
    public ComboBox<Country> countryComboBox;
    public ComboBox<Division> divisionComboBox;
    public Label divisionLabel;
    public Label custUpdateLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            countryComboBox.setItems(CountryDaoImpl.getAllCountries());
            if (createCust) {

                custUpdateLabel.setText("New Customer");
                custButt.setText("Create");
            } else {
                custUpdateLabel.setText("Customer Update");
                custNameField.setText(String.valueOf(customerData.getCustomerName()));
                countryComboBox.setValue(CountryDivOrg.getCountryByDivisionID(customerData.getDivisionId()));
                divisionComboBox.setItems(CountryDivOrg.getDivisionsByCountry(countryComboBox.getValue().getCountryId()));
                divisionComboBox.setValue(DivisionDaoImpl.getDivision(customerData.getDivisionId()));
                custAddressField.setText(String.valueOf(customerData.getAddress()));
                custPostalField.setText(String.valueOf(customerData.getPostalCode()));
                custPhoneField.setText(String.valueOf(customerData.getPhone()));
                custButt.setText("Update");
            }
            if (countryComboBox.getValue() == null) {
                divisionLabel.setText(" ");
            } else if (countryComboBox.getValue().getCountryId() == 1) {
                divisionLabel.setText("State: ");
            } else if (countryComboBox.getValue().getCountryId() == 2 || countryComboBox.getValue().getCountryId() == 3) {
                divisionLabel.setText("Province: ");
            }
        } catch (Exception e) {

        }
    }

    /**
     * <p>Creates scene for Customers.fxml</p>
     *
     * @param actionEvent the actionEvent to set
     * @throws IOException
     */
    public void custCancelButt(ActionEvent actionEvent) throws IOException {
        Scheduler.navigate(actionEvent, "Customers.fxml");
    }

    /**
     * <p>Depending on the value of static variable createCust. true will insert customer information. false with update customer information.
     * Alert appears if any fields are empty or null</p>
     *
     * @param actionEvent  the actionEvent to set
     * @throws SQLException
     * @throws IOException
     */
    public void custButt(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            DBConnection.openConnection();
            if (custNameField.getText().isEmpty() || custAddressField.getText().isEmpty() || custPostalField.getText().isEmpty() || custPhoneField.getText().isEmpty() || divisionComboBox.getSelectionModel().getSelectedItem() == null || countryComboBox.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty Field");
                alert.setContentText("Form must be completely filled before submission");
                alert.showAndWait();
                DBConnection.closeConnection();
            } else {
                if (createCust) {
                    CustomerDaoImpl.insertCustomer(custNameField.getText(), custAddressField.getText(), custPostalField.getText(), custPhoneField.getText(), divisionComboBox.getValue().getDivisionId());
                    DBConnection.closeConnection();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("New Customer");
                    alert.setContentText("New customer has been added.");
                    alert.showAndWait();
                    Scheduler.navigate(actionEvent, "Customers.fxml");
                } else {
                    CustomerDaoImpl.updateCustomer(custNameField.getText(), custAddressField.getText(), custPostalField.getText(), custPhoneField.getText(), divisionComboBox.getValue().getDivisionId(), customerData.getCustomerId());
                    DBConnection.closeConnection();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Update Customer");
                    alert.setContentText("Customer information has been updated.");
                    alert.showAndWait();
                    Scheduler.navigate(actionEvent, "Customers.fxml");
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Field");
            alert.setContentText("Form must be completely filled before submission");
            alert.showAndWait();
            e.printStackTrace();
            DBConnection.closeConnection();
        }

    }

    /**
     * <p>Division items within divisionComboBox are populated depending on the selected item from the countryComboBox.</p>
     *
     * @param actionEvent  the actionEvent to set
     * @throws Exception
     */
    public void countryComboBox(ActionEvent actionEvent) throws Exception {
        DBConnection.openConnection();
        try {
            divisionComboBox.setValue(null);
            divisionComboBox.getItems().clear();
            divisionComboBox.setItems(CountryDivOrg.getDivisionsByCountry(countryComboBox.getValue().getCountryId()));
            if (countryComboBox.getValue().getCountryId() == 1) {
                divisionLabel.setText("State: ");
            } else if (countryComboBox.getValue().getCountryId() == 2 || countryComboBox.getValue().getCountryId() == 3) {
                divisionLabel.setText("Province: ");
            }
        } catch (Exception e) {

        }
        DBConnection.closeConnection();
    }
}
