package com.nguyen.capstonecrm.controller;

import com.nguyen.capstonecrm.DAO.AppointmentDaoImpl;
import com.nguyen.capstonecrm.DAO.ContactDAOImpl;
import com.nguyen.capstonecrm.DAO.DBConnection;
import com.nguyen.capstonecrm.Scheduler;
import com.nguyen.capstonecrm.helper.Alerts;
import com.nguyen.capstonecrm.helper.TimeHandler;
import com.nguyen.capstonecrm.helper.VerifyUser;
import com.nguyen.capstonecrm.model.Contacts;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static com.nguyen.capstonecrm.controller.AppointmentsController.appointmentData;
import static com.nguyen.capstonecrm.controller.CustomersController.*;
import static java.time.LocalDateTime.parse;

/**
 * Controller for AppointmentUpdate.fxml
 */
public class AppointmentUpdateController implements Initializable {

    public ComboBox<Contacts> appContactComboBox;
    public Button updateButt;
    public TextField appTitleField;
    public TextArea appDescriptionField;
    public TextField appLocationField;
    public TextField appTypeField;
    public DatePicker appDatePicker;
    public ComboBox<LocalTime> appStartCombo;
    public ComboBox<LocalTime> appEndCombo;
    public Label appChangeLabel;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appContactComboBox.setItems(ContactDAOImpl.getAllContacts());

            TimeHandler.fillTimeBox(appStartCombo);
            TimeHandler.fillTimeBox(appEndCombo);

            if (createApp) {
                appChangeLabel.setText("New Appointment");
                updateButt.setText("Create");
            } else {
                appTitleField.setText(String.valueOf(appointmentData.getTitle()));
                appDescriptionField.setText(String.valueOf(appointmentData.getDescription()));
                appLocationField.setText(String.valueOf(appointmentData.getLocation()));
                appTypeField.setText(String.valueOf(appointmentData.getType()));
                appContactComboBox.setValue(ContactDAOImpl.getContactByID(appointmentData.getContactId()));
                appDatePicker.setValue(appointmentData.getStartDateTime().toLocalDateTime().toLocalDate());
                appStartCombo.setValue(appointmentData.getStartDateTime().toLocalDateTime().toLocalTime());
                appEndCombo.setValue(appointmentData.getEndDateTime().toLocalDateTime().toLocalTime());
                appChangeLabel.setText("Update Appointment");
                updateButt.setText("Update");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Inserts or Updates database appointments table based on the value of static variable createApp. true will insert the appointment data.
     * false will update the appointment data.</p>
     *
     * @param actionEvent the actionEvent to set
     * @throws SQLException
     * @throws IOException
     */
    public void updateButt(ActionEvent actionEvent) throws SQLException, IOException {
        DBConnection.openConnection();
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            Timestamp startDateTime = Timestamp.valueOf(parse((appDatePicker.getValue().toString() + " " + appStartCombo.getValue().toString()), dtf));
            Timestamp endDateTime = Timestamp.valueOf(parse((appDatePicker.getValue().toString() + " " + appEndCombo.getValue().toString()), dtf));

            if (startDateTime.after(endDateTime) || startDateTime.equals(endDateTime)) {
                Alerts.alertWarning("Invalid Start Time","Appointment can't start at the same time or after the end time.");
                DBConnection.closeConnection();
            } else if(endDateTime.before(startDateTime)) {
                Alerts.alertWarning("Invalid Start Time","Appointment can't have an end time before the start time.");
                DBConnection.closeConnection();
            }
            else if (appTitleField.getText().isEmpty() || appDescriptionField.getText().isEmpty() || appLocationField.getText().isEmpty() || appTypeField.getText().isEmpty() || appContactComboBox.getValue() == null || appDatePicker.getValue() == null || appStartCombo.getSelectionModel().getSelectedItem() == null || appEndCombo.getSelectionModel().getSelectedItem() == null) {
                Alerts.alertWarning("Empty Field","Form must be completely filled before submission");
                DBConnection.closeConnection();
            } else {
                if (!TimeHandler.verifyBusinessHours(startDateTime, endDateTime)) {
                    Alerts.alertWarning("Invalid Day/Time","You can't make an appointment outside of office hours.");
                    DBConnection.closeConnection();
                } else {
                    if (createApp) {
                        if (TimeHandler.appointmentOverlapCheck(startDateTime, endDateTime)) {
                            AppointmentDaoImpl.insertAppointment(appTitleField.getText(), appDescriptionField.getText(), appLocationField.getText(), appTypeField.getText(), startDateTime, endDateTime, appContactComboBox.getValue().getContactId());
                            DBConnection.closeConnection();
                            Alerts.alertConfirmation(actionEvent,"New Appointment","New appointment has been added.","Appointments.fxml");
                        } else {
                            DBConnection.closeConnection();
                        }
                    } else {
                        if (TimeHandler.appointmentOverlapCheck(startDateTime, endDateTime)) {
                            AppointmentDaoImpl.updateAppointment(AppointmentsController.appointmentData.getAppointmentId(), appTitleField.getText(), appDescriptionField.getText(), appLocationField.getText(), appTypeField.getText(), startDateTime, endDateTime, appointmentData.getCustomerId(), VerifyUser.currentUser.getUserID(), appContactComboBox.getValue().getContactId());
                            DBConnection.closeConnection();
                            Alerts.alertConfirmation(actionEvent,"Update Appointment","Appointment information has been updated.","Appointments.fxml");
                        } else {
                            DBConnection.closeConnection();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Alerts.alertWarning("Empty Field","Form must be completely filled before submission");
            e.printStackTrace();
            DBConnection.closeConnection();
        }
    }

    /**
     * <p>Creates scene based to static variable createApp. true will create scene for Customers.fxml. false with create scene for Appointments.fxml</p>
     *
     * @param actionEvent the actionEvent to set
     * @throws IOException
     */
    public void appCancelButt(ActionEvent actionEvent) throws IOException {
        if (createApp) {
            Scheduler.navigate(actionEvent, "Customers.fxml");
        } else {
            Scheduler.navigate(actionEvent, "Appointments.fxml");
        }
    }
}
