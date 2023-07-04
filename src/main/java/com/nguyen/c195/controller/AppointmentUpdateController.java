package com.nguyen.c195.controller;

import com.nguyen.c195.DAO.AppointmentDaoImpl;
import com.nguyen.c195.DAO.ContactDAOImpl;
import com.nguyen.c195.DAO.DBConnection;
import com.nguyen.c195.Scheduler;
import com.nguyen.c195.helper.TimeHandler;
import com.nguyen.c195.helper.VerifyUser;
import com.nguyen.c195.model.Contacts;
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

import static com.nguyen.c195.controller.AppointmentsController.appointmentData;
import static com.nguyen.c195.controller.CustomersController.createApp;
import static com.nguyen.c195.controller.CustomersController.customerData;
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
                appContactComboBox.setValue(ContactDAOImpl.getContactByID(appointmentData.getContacts().getContactId()));
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
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Start Time");
                alert.setContentText("Appointment can't start at the same time or after the end time.");
                alert.showAndWait();
                DBConnection.closeConnection();
            } else if (appTitleField.getText().isEmpty() || appDescriptionField.getText().isEmpty() || appLocationField.getText().isEmpty() || appTypeField.getText().isEmpty() || appContactComboBox.getValue() == null || appDatePicker.getValue() == null || appStartCombo.getSelectionModel().getSelectedItem() == null || appEndCombo.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty Field");
                alert.setContentText("Form must be completely filled before submission");
                alert.showAndWait();
                DBConnection.closeConnection();
            } else {
                if (!TimeHandler.verifyBusinessHours(startDateTime, endDateTime)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid Day/Time");
                    alert.setContentText("You can't make an appointment outside of office hours");
                    alert.showAndWait();
                    DBConnection.closeConnection();
                } else {
                    if (createApp) {
                        if (TimeHandler.appointmentOverlapCheck(startDateTime, endDateTime)) {
                            AppointmentDaoImpl.insertAppointment(appTitleField.getText(), appDescriptionField.getText(), appLocationField.getText(), appTypeField.getText(), startDateTime, endDateTime, customerData.getCustomerId(), VerifyUser.loginID, appContactComboBox.getValue().getContactId());
                            DBConnection.closeConnection();
                            Scheduler.navigate(actionEvent, "Appointments.fxml");
                        } else {
                            DBConnection.closeConnection();
                        }
                    } else {
                        AppointmentDaoImpl.updateAppointment(AppointmentsController.appointmentData.getAppointmentId(), appTitleField.getText(), appDescriptionField.getText(), appLocationField.getText(), appTypeField.getText(), startDateTime, endDateTime, appointmentData.getCustomerId(), VerifyUser.loginID, appContactComboBox.getValue().getContactId());
                        DBConnection.closeConnection();
                        Scheduler.navigate(actionEvent, "Appointments.fxml");
                    }
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Field");
            alert.setContentText("Form must be completely filled before submission");
            alert.showAndWait();
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
