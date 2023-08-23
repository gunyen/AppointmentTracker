package com.nguyen.capstonecrm.controller;

import com.nguyen.capstonecrm.DAO.AppointmentDaoImpl;
import com.nguyen.capstonecrm.DAO.DBConnection;
import com.nguyen.capstonecrm.helper.Alerts;
import com.nguyen.capstonecrm.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
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

import static com.nguyen.capstonecrm.Scheduler.navigate;

/**
 * Controller for Appointments.fxml
 */
public class AppointmentsController implements Initializable {

    public RadioButton appSortWeekRad;
    public RadioButton appSortMonthRad;
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
    protected static Appointment appointmentData;

    ObservableList<Appointment> Appointments = FXCollections.observableArrayList();

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initAppointmentTable("week");
    }

    /**
     * <p>Populates AppointmentTable tableview with an ObservableList of Appointment objects queried from database</p>
     *
     * @param dates the dates to set
     */
    private void initAppointmentTable(String dates) {
        try {
            AppID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            Title.setCellValueFactory(new PropertyValueFactory<>("title"));
            Description.setCellValueFactory(new PropertyValueFactory<>("description"));
            Location.setCellValueFactory(new PropertyValueFactory<>("location"));
            Contact.setCellValueFactory(new PropertyValueFactory<>("contacts"));
            Type.setCellValueFactory(new PropertyValueFactory<>("type"));
            Start.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            End.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            UserID.setCellValueFactory(new PropertyValueFactory<>("userId"));

            Appointments.addAll(AppointmentDaoImpl.getAllAppointments(dates));
        } catch (Exception e) {
            Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, e);
        }
        AppointmentTable.setItems(Appointments);
    }

    /**
     * <p>Repopulates the AppointmentTable tableview set items with the ObservableList of Appointment object within current week</p>
     *
     * @param actionEvent the actionEvent to set
     */
    public void appSortWeekRad(ActionEvent actionEvent) {
        DBConnection.openConnection();
        if (appSortWeekRad.isSelected()) {
            AppointmentTable.getItems().clear();
            initAppointmentTable("week");
        }
        DBConnection.closeConnection();
    }

    /**
     * <p>Repopulates AppointmentTable tableview set items with ObservableList of Appointment object within current month</p>
     *
     * @param actionEvent the actionEvent to set
     */
    public void appSortMonthRad(ActionEvent actionEvent) {
        DBConnection.openConnection();
        if (appSortMonthRad.isSelected()) {
            AppointmentTable.getItems().clear();
            initAppointmentTable("month");
        }
        DBConnection.closeConnection();
    }

    /**
     * <p>Creates a scene for Customers.fxml</p>
     *
     * @param actionEvent the actionEvent to set
     * @throws IOException
     */
    public void customerButton(ActionEvent actionEvent) throws IOException {
        navigate(actionEvent, "Customers.fxml");
    }

    /**
     * <p>Creates scene for AppointmentUpdate.fxml to update existing data from selected item in AppointmentTable tableview.</p>
     *
     * @param actionEvent the actionEvent to set
     * @throws IOException
     */
    public void appEditButt(ActionEvent actionEvent) throws IOException {
        DBConnection.openConnection();
        if (AppointmentTable.getSelectionModel().isEmpty()) {
            Alerts.alertWarning("Missing Information","Appointment must be selected for editing.");
        } else {
            CustomersController.createApp = false;
            getAppointmentData(AppointmentTable.getSelectionModel().getSelectedItem());
            DBConnection.closeConnection();
            navigate(actionEvent, "AppointmentUpdate.fxml");
        }
    }

    /**
     * <p>Deletes selected appointment from AppointmentTable tableview</p>
     *
     * @param actionEvent the actionEvent to set
     * @throws SQLException
     */
    public void appDeleteButt(ActionEvent actionEvent) throws SQLException {
        DBConnection.openConnection();
        if (AppointmentTable.getSelectionModel().isEmpty()) {
            Alerts.alertWarning("Missing Appointment","Must select an appointment to delete.");
        } else {
            Optional<ButtonType> result = Alerts.alertInfoOk("Appointment Deletion","Are you sure you want to delete this appointment?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Alerts.alertInfo("Appointment Deletion",AppointmentTable.getSelectionModel().getSelectedItem().getAppointmentId() + ": " + AppointmentTable.getSelectionModel().getSelectedItem().getType() + " has been deleted");
                AppointmentDaoImpl.deleteAppointment(AppointmentTable.getSelectionModel().getSelectedItem().getAppointmentId());
                AppointmentTable.getItems().clear();
                if (appSortWeekRad.isSelected()) {
                    initAppointmentTable("week");
                } else if (appSortMonthRad.isSelected()) {
                    initAppointmentTable("month");
                }
            }
        }
        DBConnection.closeConnection();
    }

    /**
     * <p>Creates scene for Login.fxml</p>
     *
     * @param actionEvent the actionEvent to set
     * @throws IOException
     */
    public void logoutButt(ActionEvent actionEvent) throws IOException {
        Alerts.alertConfirmation(actionEvent,"Log Out","Are you sure you want to logout?","Login.fxml");
    }

    /**
     * <p>Returns Appointment object from static variable based on set appointment</p>
     *
     * @param appointment the appointment to set
     * @return appointment
     */
    public Appointment getAppointmentData(Appointment appointment) {
        appointmentData = appointment;
        return appointmentData;
    }

    /**
     * <p>Creates scene for Reporting.fxml</p>
     *
     * @param actionEvent the actionEvent to set
     * @throws IOException
     */
    public void reportsButton(ActionEvent actionEvent) throws IOException {
        navigate(actionEvent, "Reporting.fxml");
    }
}
