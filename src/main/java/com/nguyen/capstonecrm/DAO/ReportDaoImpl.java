package com.nguyen.capstonecrm.DAO;

import com.nguyen.capstonecrm.model.Contacts;
import com.nguyen.capstonecrm.model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Month;

/**
 * Report Data Access Object
 */
public abstract class ReportDaoImpl {

    /**
     * @return
     * @throws SQLException
     */
    public static ObservableList<Report> getAppointmentReport() throws SQLException {
        ObservableList<Report> allAppointment = FXCollections.observableArrayList();
        String sqlStatement = "SELECT DISTINCT Type, MONTH(Start) startMonth, COUNT(Type) Total FROM appointments GROUP BY Type, MONTH(Start)";
        Query.makeQuery(sqlStatement);
        ResultSet rs = Query.getResult();
        while (rs.next()) {
            String type = rs.getString("Type");
            int total = rs.getInt("Total");
            Month month = Month.of((int) rs.getLong("startMonth"));

            Report report = new Report(type, month, total);
            allAppointment.add(report);
        }

        return allAppointment;
    }

    /**
     * @param contact
     * @return
     * @throws SQLException
     */
    public static ObservableList<Report> getScheduleReport(Contacts contact) throws SQLException {
        ObservableList<Report> allReport = FXCollections.observableArrayList();
        String sqlStatement = String.format("SELECT * FROM appointments WHERE Contact_ID=%s AND Start > CURDATE() ", contact.getContactId());
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int appointmentId = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String type = result.getString("Type");
            Timestamp starts = result.getTimestamp("Start");
            Timestamp ends = result.getTimestamp("End");
            int customerID = result.getInt("Customer_ID");

            Report report = new Report(type, contact, appointmentId, title, description, starts, ends, customerID);
            allReport.add(report);
        }
        return allReport;
    }
}
