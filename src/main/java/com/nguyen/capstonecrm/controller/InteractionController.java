package com.nguyen.capstonecrm.controller;

import com.nguyen.capstonecrm.DAO.CommunicationDaoImpl;
import com.nguyen.capstonecrm.DAO.DBConnection;
import com.nguyen.capstonecrm.DAO.WorklogDaoImpl;
import com.nguyen.capstonecrm.Scheduler;
import com.nguyen.capstonecrm.helper.Alerts;
import com.nguyen.capstonecrm.model.CustInfo;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 *Controller for Interaction.fxml
 */
public class InteractionController implements Initializable {
    public TextField custinfoTypeField;
    public TextArea custinfoDescField;
    public DatePicker custinfoDateField;
    public RadioButton logOpenRad;
    public RadioButton logClosedRad;
    public Button custinfoSubmitButt;
    public GridPane infoFormGraph;
    public Label infoStatusLabel;
    public ToggleGroup statusRadGroup;
    public Label interactionLabel;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        worklogInit();
        commInit();
        if(CustInfo.getInteraction()==1 || CustInfo.getInteraction()==2) {
            interactionLabel.setText("Work Log");
        } else if(CustInfo.getInteraction()==3 || CustInfo.getInteraction()==4) {
            interactionLabel.setText("Communication");
        }
    }

    /**
     *
     */
    private void worklogInit(){
        if(CustInfo.getInteraction()==1) {
            custinfoSubmitButt.setText("Create");
            statusVisible(true);

        } else if(CustInfo.getInteraction()==2){
            custinfoSubmitButt.setText("Update");
            statusVisible(true);
            custinfoTypeField.setText(CustomerInfoController.worklogData.getType());
            custinfoDescField.setText(CustomerInfoController.worklogData.getDesc());
            custinfoDateField.setValue(CustomerInfoController.worklogData.getWhen());
            if (CustomerInfoController.worklogData.getStatus().equals("Open")) {
                logOpenRad.setSelected(true);
                logClosedRad.setSelected(false);
            } else if (CustomerInfoController.worklogData.getStatus().equals("Closed")) {
                logOpenRad.setSelected(false);
                logClosedRad.setSelected(true);
            }
        }
    }

    /**
     *
     */
    private void commInit(){
        if(CustInfo.getInteraction()==3) {
            custinfoSubmitButt.setText("Create");
            statusVisible(false);
        } else if(CustInfo.getInteraction()==4) {
            custinfoSubmitButt.setText("Update");
            statusVisible(false);
            custinfoTypeField.setText(CustomerInfoController.commData.getType());
            custinfoDescField.setText(CustomerInfoController.commData.getDesc());
            custinfoDateField.setValue(CustomerInfoController.commData.getWhen());
        }
    }


    /**
     * @param actionEvent
     * @throws IOException
     */
    public void custinfoSubmitButt(ActionEvent actionEvent) throws IOException {
        DBConnection.openConnection();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(custinfoTypeField.getText().isEmpty() || custinfoDescField.getText().isEmpty() || custinfoDateField.getValue()==null) {
            Alerts.alertWarning("Empty Field","Form must be completely filled before submission");
        } else {
            String status="";
            if (logOpenRad.isSelected()) {
                status = "Open";
            } else if (logClosedRad.isSelected()) {
                status = "Closed";
            }
            if(CustInfo.getInteraction()==1) {
                WorklogDaoImpl.insert(custinfoTypeField.getText(), custinfoDescField.getText(),  custinfoDateField.getValue().format(date), status, CustomersController.customerData.getCustomerID());
                DBConnection.closeConnection();
            } else if(CustInfo.getInteraction()==2) {
                WorklogDaoImpl.update(CustomerInfoController.worklogData.getCustInfoID(), custinfoTypeField.getText(), custinfoDescField.getText(), custinfoDateField.getValue().format(date),status);
                DBConnection.closeConnection();
            } else if(CustInfo.getInteraction()==3) {
                CommunicationDaoImpl.insert(custinfoTypeField.getText(), custinfoDescField.getText(), custinfoDateField.getValue().format(date), CustomersController.customerData.getCustomerID());
                DBConnection.closeConnection();
            } else if(CustInfo.getInteraction()==4) {
                CommunicationDaoImpl.update(CustomerInfoController.commData.getCustInfoID(), custinfoTypeField.getText(), custinfoDescField.getText(), custinfoDateField.getValue().format(date));
                DBConnection.closeConnection();
            }
        }
        Scheduler.navigate(actionEvent, "CustomerInfo.fxml");
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void custinfoCancelButt(ActionEvent actionEvent) throws IOException {
        Scheduler.navigate(actionEvent, "CustomerInfo.fxml");
    }

    /**
     * @param visible
     */
    public void statusVisible(Boolean visible) {
        infoStatusLabel.setVisible(visible);
        logOpenRad.setVisible(visible);
        logClosedRad.setVisible(visible);
    }
}
