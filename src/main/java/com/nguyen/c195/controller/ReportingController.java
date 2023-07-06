package com.nguyen.c195.controller;

import com.nguyen.c195.DAO.ContactDAOImpl;
import com.nguyen.c195.DAO.DBConnection;
import com.nguyen.c195.DAO.ReportDaoImpl;
import com.nguyen.c195.Scheduler;
import com.nguyen.c195.helper.OutputReportInterface;
import com.nguyen.c195.model.Contacts;
import com.nguyen.c195.model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Controller for Reporting.fxml
 */
public class ReportingController implements Initializable {
    public ComboBox<Contacts> contactComboBox;
    public TableView<Report> totalAppTable;
    public TableColumn<?, ?> firstTypeCol;
    public TableColumn<?, ?> firstMonthCol;
    public TableColumn<?, ?> firstTotalCol;
    public TableView<Report> contactSchedTable;
    public TableColumn<?, ?> secondContactCol;
    public TableColumn<?, ?> secondAppIdCol;
    public TableColumn<?, ?> secondTitleCol;
    public TableColumn<?, ?> secondTypeCol;
    public TableColumn<?, ?> secondDescriptionCol;
    public TableColumn<Report, ?> secondStartCol;
    public TableColumn<?, ?> secondEndCol;
    public TableColumn<?, ?> secondCustIdCol;
    public TableView<Report> loginAttemptTable;
    public TableColumn<?, ?> thirdUserIdCol;
    public TableColumn<?, ?> thirdMonthCol;
    public TableColumn<Report, ?> thirdStampCol;
    public TableColumn<?, ?> thirdSuccessCol;
    public ObservableList<Report> appReport = FXCollections.observableArrayList();
    public ObservableList<Report> scheduleReport = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection.openConnection();
        try {
            firstTab();
            contactComboBox.setItems(ContactDAOImpl.getAllContacts());
            contactSchedTable.setPlaceholder(new Label("Select a contact to view their schedule"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        DBConnection.closeConnection();
    }

    /**
     * <p>Populate totalAppTable tableview</p>
     *
     * @throws Exception
     */
    public void firstTab() throws Exception {
        try {
            contactComboBox.setVisible(false);

            firstTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            firstMonthCol.setCellValueFactory(new PropertyValueFactory<>("month2"));
            firstTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

            appReport.addAll(ReportDaoImpl.getAppointmentReport());

            totalAppTable.setItems(appReport);

        } catch (Exception e) {

        }
    }

    /**
     * <p>Makes contactComboBox visible for use</p>
     *
     * @throws Exception
     */
    public void secondTab() throws Exception {
        contactComboBox.setVisible(true);
    }

    /**
     * <p>Retrieves information from login_activity.txt to create an ObservableList of login activity and populates loginAttemptTable tableview.
     * Lambda expression using OutputReportInterface functional interface returns ObservableList.</p>
     *
     * @throws Exception
     */
    public void thirdTab() throws Exception {
        DBConnection.openConnection();
        try {
            contactComboBox.setVisible(false);
            OutputReportInterface output = () -> {
                ObservableList<Report> allAttempts = FXCollections.observableArrayList();
                String filename = "login_activity.txt", attempt;

                File file = new File(filename);

                if (!file.exists()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Missing log file!");
                    alert.setContentText("Log file has ran away from home!");
                    alert.showAndWait();
                }
                Scanner inputFile = new Scanner(file);

                while (inputFile.hasNext()) {
                    attempt = inputFile.nextLine();

                    String[] arrOfString = attempt.split(" : ");
                    allAttempts.add(new Report(arrOfString[0], LocalDate.parse(arrOfString[1]).getMonth(), Timestamp.valueOf(arrOfString[2]), arrOfString[3]));
                }

                inputFile.close();
                return allAttempts;
            };

            thirdUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
            thirdMonthCol.setCellValueFactory(new PropertyValueFactory<>("month2"));
            thirdStampCol.setCellValueFactory(new PropertyValueFactory<>("login"));
            thirdSuccessCol.setCellValueFactory(new PropertyValueFactory<>("success"));

            loginAttemptTable.setItems(output.outputReport());
            loginAttemptTable.getSortOrder().add(thirdStampCol);
        } catch (Exception e) {

        }
        DBConnection.closeConnection();
    }

    /**
     * <p>Closes current scene and creates a scene for Appointments.fxml</p>
     *
     * @param actionEvent  the actionEvent to set
     * @throws IOException
     */
    public void backButton(ActionEvent actionEvent) throws IOException {
        Scheduler.navigate(actionEvent, "Appointments.fxml");
    }

    /**
     * <p>Populates contactSchedTable tableview when an item is selected within the combo box</p>
     *
     * @param actionEvent the actionEvent to set
     */
    public void contactComboBox(ActionEvent actionEvent) {
        DBConnection.openConnection();
        try {
            contactSchedTable.getItems().clear();
            contactSchedTable.setPlaceholder(new Label("Select a contact to view their schedule"));

            contactComboBox.setVisible(true);

            secondContactCol.setCellValueFactory(new PropertyValueFactory<>("contacts"));
            secondAppIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            secondTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            secondTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            secondDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            secondStartCol.setCellValueFactory(new PropertyValueFactory<>("starts"));
            secondEndCol.setCellValueFactory(new PropertyValueFactory<>("ends"));
            secondCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            scheduleReport.addAll(ReportDaoImpl.getScheduleReport(contactComboBox.getSelectionModel().getSelectedItem()));
            contactSchedTable.setItems(scheduleReport);
            contactSchedTable.getSortOrder().add(secondStartCol);

        } catch (Exception e) {

        }
        DBConnection.closeConnection();
    }
}
