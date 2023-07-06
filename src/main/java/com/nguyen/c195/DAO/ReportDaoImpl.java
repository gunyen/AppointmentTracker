package com.nguyen.c195.DAO;

import com.nguyen.c195.model.Contacts;
import com.nguyen.c195.model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Month;

/**
 * Report Data Access Object
 */
public abstract class ReportDaoImpl {


    /**
     * <p>Returns an ObservableList of Report objects querying the database. Collects Reports grouped by month</p>
     *
     * @return allAppointment
     * @throws SQLException
     */
    public static ObservableList<Report> getAppointmentReport() throws SQLException {
        ObservableList<Report> allAppointment = FXCollections.observableArrayList();
        String sqlStatement = "SELECT DISTINCT Type, MONTH(Start) startMonth, COUNT(Type) Total FROM appointments GROUP BY Type, MONTH(Start)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sqlStatement);

        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            String type = rs.getString("Type");
            long start = rs.getLong("startMonth");
            int total = rs.getInt("Total");
            Month month = Month.of((int) rs.getLong("startMonth"));

            Report report = new Report(type, month, total);
            allAppointment.add(report);
        }

        return allAppointment;
    }

    /**
     * <p>Returns an ObservableList of Report objects based on set contact to query. Collects Report objects after current localdate.</p>
     *
     * @param contact the contact to set
     * @return allReport
     * @throws SQLException
     */
    public static ObservableList<Report> getScheduleReport(Contacts contact) throws SQLException {
        ObservableList<Report> allReport = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM appointments WHERE Contact_ID=? AND Start > CURDATE() ";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sqlStatement);
        ps.setInt(1, contact.getContactId());
        ps.executeQuery();
        ResultSet result = ps.getResultSet();
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
