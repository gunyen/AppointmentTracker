package com.nguyen.capstonecrm.controller;

import com.nguyen.capstonecrm.DAO.CountryDaoImpl;
import com.nguyen.capstonecrm.DAO.CustomerDaoImpl;
import com.nguyen.capstonecrm.DAO.DBConnection;
import com.nguyen.capstonecrm.DAO.DivisionDaoImpl;
import com.nguyen.capstonecrm.Scheduler;
import com.nguyen.capstonecrm.helper.Alerts;
import com.nguyen.capstonecrm.helper.CountryDivOrg;
import com.nguyen.capstonecrm.model.Country;
import com.nguyen.capstonecrm.model.Division;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.nguyen.capstonecrm.controller.CustomersController.*;

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
    public TextField emailTextField;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryComboBox.setItems(CountryDaoImpl.getAllCountries());
            if (createCustomer) {

                custUpdateLabel.setText("New Customer");
                custButt.setText("Create");
            } else {
                custUpdateLabel.setText("Customer Update");
                custNameField.setText(String.valueOf(customerData.getName()));
                countryComboBox.setValue(CountryDivOrg.getCountryByDivisionID(customerData.getDivisionID()));
                divisionComboBox.setItems(CountryDivOrg.getDivisionsByCountry(countryComboBox.getValue().getGeoID()));
                divisionComboBox.setValue(DivisionDaoImpl.getDivision(customerData.getDivisionID()));
                custAddressField.setText(String.valueOf(customerData.getAddress()));
                custPostalField.setText(String.valueOf(customerData.getPostalCode()));
                custPhoneField.setText(String.valueOf(customerData.getPhone()));
                emailTextField.setText(String.valueOf(customerData.getEmail()));
                custButt.setText("Update");
            }
            if (countryComboBox.getValue() == null) {
                divisionLabel.setText(" ");
            } else if (countryComboBox.getValue().getGeoID() == 1) {
                divisionLabel.setText("State: ");
            } else if (countryComboBox.getValue().getGeoID() == 2 || countryComboBox.getValue().getGeoID() == 3) {
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
        if(createCustomer) {
            Scheduler.navigate(actionEvent, "Customers.fxml");
        } else {
            Scheduler.navigate(actionEvent, "CustomerInfo.fxml");
        }
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
            if (custNameField.getText().isEmpty() || custAddressField.getText().isEmpty() || custPostalField.getText().isEmpty() || custPhoneField.getText().isEmpty() || emailTextField.getText().isEmpty() || divisionComboBox.getSelectionModel().getSelectedItem() == null || countryComboBox.getSelectionModel().getSelectedItem() == null) {
                Alerts.alertWarning("Empty Field","Form must be completely filled before submission");
                DBConnection.closeConnection();
            } else {
                if (createCustomer) {
                    CustomerDaoImpl.insert(custNameField.getText(), custAddressField.getText(), custPostalField.getText(), custPhoneField.getText(), emailTextField.getText(), divisionComboBox.getValue().getGeoID());
                    DBConnection.closeConnection();
                    Alerts.alertConfirmation(actionEvent,"New Customer","New customer has been added.","Customers.fxml");
                } else {
                    CustomerDaoImpl.update(custNameField.getText(), custAddressField.getText(), custPostalField.getText(), custPhoneField.getText(), emailTextField.getText(), divisionComboBox.getValue().getGeoID(), customerData.getCustomerID());
                    DBConnection.closeConnection();
                    Alerts.alertConfirmation(actionEvent,"Update Customer","Customer information has been updated.","Customers.fxml");
                }
            }
        } catch (Exception e) {
            Alerts.alertWarning("Empty Field","Form must be completely filled before submission");
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
            divisionComboBox.setItems(CountryDivOrg.getDivisionsByCountry(countryComboBox.getValue().getGeoID()));
            if (countryComboBox.getValue().getGeoID() == 1) {
                divisionLabel.setText("State: ");
            } else if (countryComboBox.getValue().getGeoID() == 2 || countryComboBox.getValue().getGeoID() == 3) {
                divisionLabel.setText("Province: ");
            }
        } catch (Exception e) {

        }
        DBConnection.closeConnection();
    }
}
