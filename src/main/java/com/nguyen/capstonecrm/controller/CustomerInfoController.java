package com.nguyen.capstonecrm.controller;

import com.nguyen.capstonecrm.DAO.*;
import com.nguyen.capstonecrm.Scheduler;
import com.nguyen.capstonecrm.helper.Alerts;
import com.nguyen.capstonecrm.helper.CountryDivOrg;
import com.nguyen.capstonecrm.model.Communication;
import com.nguyen.capstonecrm.model.CustInfo;
import com.nguyen.capstonecrm.model.Worklog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.nguyen.capstonecrm.controller.CustomersController.createCustomer;
import static com.nguyen.capstonecrm.controller.CustomersController.customerData;

/**
 *Controller for CustomerInfo.fxml
 */
public class CustomerInfoController implements Initializable {
    public Label divisionLabel;
    public Label infoNameLabel;
    public Label infoEmailLabel;
    public Label infoPhoneLabel;
    public Label infoAddressLabel;
    public Label infoCountryLabel;
    public Label infoDivisionLabel;
    public TableView<Worklog> worklogTable;
    public TableColumn<?,?> workLogCol;
    public TableColumn<?,?> workDateCol;
    public TableColumn<?,?> workTypeCol;
    public TableColumn<?,?> workDescCol;
    public TableColumn<?,?> workStatusCol;
    public TableView<Communication> communicationTable;
    public TableColumn<?,?> commTypeCol;
    public TableColumn<?,?> commDescCol;
    public TableColumn<?,?> commDateCol;

    private int logFilter=1;
    protected static Worklog worklogData;
    protected static Communication commData;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateInfo();
            initWorklogTable();
            initCommTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    private void initWorklogTable(){
        ObservableList<Worklog> Worklogs = FXCollections.observableArrayList();
        try {
            workLogCol.setCellValueFactory(new PropertyValueFactory<>("custInfoID"));
            workDateCol.setCellValueFactory(new PropertyValueFactory<>("when"));
            workTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            workDescCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
            workStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

            Worklogs.addAll(WorklogDaoImpl.getAllWorklogs(logFilter, customerData.getCustomerID()));
        } catch (Exception e) {
            
        }
        worklogTable.setItems(Worklogs);
    }

    /**
     *
     */
    private void initCommTable(){
        ObservableList<Communication> Communcications = FXCollections.observableArrayList();
        try {
            commTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            commDescCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
            commDateCol.setCellValueFactory(new PropertyValueFactory<>("when"));

            Communcications.addAll(CommunicationDaoImpl.getAllCommunications(customerData.getCustomerID()));
        } catch (Exception e) {
            
        }
        communicationTable.setItems(Communcications);
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void infoBackButt(ActionEvent actionEvent) throws IOException {
        Scheduler.navigate(actionEvent, "Customers.fxml");
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void infoEditButt(ActionEvent actionEvent) throws IOException {
        try {
            CustInfo.setInteraction(0);
            if (customerData==null) {
                Alerts.alertWarning("Missing Customer Information","Oh no! Someone got lost in the network :O");
            } else {
                DBConnection.closeConnection();
                createCustomer=false;
                Scheduler.navigate(actionEvent, "CustomerUpdate.fxml");
            }
        } catch (Exception e) {

        }
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void logInsertButt(ActionEvent actionEvent) throws IOException {
        CustInfo.setInteraction(1);
        Scheduler.navigate(actionEvent, "Interaction.fxml");
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void logEditButt(ActionEvent actionEvent) throws IOException {
        getWorklogData(worklogTable.getSelectionModel().getSelectedItem());
        if(worklogData==null) {
            Alerts.alertWarning("Worklog not selected","A work log must be selected for editing.");
        } else {
            CustInfo.setInteraction(2);
            Scheduler.navigate(actionEvent, "Interaction.fxml");
        }
    }

    /**
     * @param actionEvent
     * @throws SQLException
     */
    public void logDeleteButt(ActionEvent actionEvent) throws SQLException, IOException {
        DBConnection.openConnection();
        if (worklogTable.getSelectionModel().isEmpty()) {
            Alerts.alertWarning("Missing Log","Must select a log to delete.");
        } else {
            Optional<ButtonType> result = Alerts.alertConfirmation("Log Deletion","Are you sure you want to delete this log?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Alerts.alertInfo("Log Deletion",(worklogTable.getSelectionModel().getSelectedItem().getCustInfoID() + "has been deleted"));
                WorklogDaoImpl.delete(worklogTable.getSelectionModel().getSelectedItem().getCustInfoID());
                worklogTable.getItems().clear();
                worklogTable.setItems(WorklogDaoImpl.getAllWorklogs(1,customerData.getCustomerID()));
            }
        }
        DBConnection.closeConnection();
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void commInsertButt(ActionEvent actionEvent) throws IOException {
        CustInfo.setInteraction(3);
        Scheduler.navigate(actionEvent, "Interaction.fxml");
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void commEditButt(ActionEvent actionEvent) throws IOException {
        getCommData(communicationTable.getSelectionModel().getSelectedItem());
        if(commData==null){
            Alerts.alertWarning("Interaction Not Selected","A communication log must be selected for editing.");
        } else {
            CustInfo.setInteraction(4);
            Scheduler.navigate(actionEvent, "Interaction.fxml");
        }
    }

    /**
     * @param actionEvent
     * @throws SQLException
     */
    public void commDeleteButt(ActionEvent actionEvent) throws SQLException, IOException {
        DBConnection.openConnection();
        if (communicationTable.getSelectionModel().isEmpty()) {
            Alerts.alertWarning("Missing Communication","Must select a log to delete.");
        } else {
            Optional<ButtonType> result = Alerts.alertConfirmation("Communicaton Deletion","Are you sure you want to delete this communicaton?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Alerts.alertInfo("Log Deletion",(communicationTable.getSelectionModel().getSelectedItem().getCustInfoID() + "has been deleted"));
                communicationTable.getItems().clear();
                communicationTable.setItems(CommunicationDaoImpl.getAllCommunications(customerData.getCustomerID()));
            }
        }
        DBConnection.closeConnection();
    }

    /**
     * @throws Exception
     */
    private void populateInfo() throws Exception {
        int countryID = DivisionDaoImpl.getDivision(customerData.getDivisionID()).getCountryID();
        try {
            infoNameLabel.setText(customerData.getName());
            infoEmailLabel.setText(customerData.getEmail());
            infoPhoneLabel.setText(customerData.getPhone());
            infoAddressLabel.setText(customerData.getAddress());
            infoCountryLabel.setText(CountryDivOrg.getCountryByDivisionID(customerData.getDivisionID()).getGeoName());
            infoDivisionLabel.setText(DivisionDaoImpl.getDivision(customerData.getDivisionID()).getGeoName());
            if (countryID == 1) {
                divisionLabel.setText("State: ");
            } else if (countryID == 2 || countryID == 3) {
                divisionLabel.setText("Province: ");
            }
        } catch (Exception e) {

        }
    }

    /**
     * @param actionEvent
     */
    public void workAllRad(ActionEvent actionEvent) {
        DBConnection.openConnection();
        logFilter=1;
        initWorklogTable();
        DBConnection.closeConnection();
    }

    /**
     * @param actionEvent
     */
    public void workInworkRad(ActionEvent actionEvent) {
        DBConnection.openConnection();
        logFilter=2;
        initWorklogTable();
        DBConnection.closeConnection();
    }

    /**
     * @param actionEvent
     */
    public void workDoneRad(ActionEvent actionEvent) {
        DBConnection.openConnection();
        logFilter=3;
        initWorklogTable();
        DBConnection.closeConnection();
    }

    /**
     * @param worklog
     */
    public void getWorklogData(Worklog worklog) {
        worklogData = worklog;
    }

    /**
     * @param comm
     */
    public void getCommData(Communication comm) {
        commData = comm;
    }
}
