package com.nguyen.capstonecrm.DAO;

import com.nguyen.capstonecrm.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static com.nguyen.capstonecrm.controller.CustomersController.customerData;
import static com.nguyen.capstonecrm.helper.VerifyUser.currentUser;


/**
 * Appointment Data Access Object
 */
public abstract class AppointmentDaoImpl {

    /**
     * <p>Inserts a row of appointment information within the database appointments table setting title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId</p>
     *
     * @param title the title to set
     * @param description the description to set
     * @param location the location to set
     * @param type the type to set
     * @param startDateTime the startDateTime to set
     * @param endDateTime the endDateTime to set
     * @param customerId the customerId to set
     * @param userId the userId to set
     * @param contactId the contactId to set
     * @throws SQLException
     */
    public static void insertAppointment(String title, String description, String location, String type, Timestamp startDateTime, Timestamp endDateTime, int contactId) throws SQLException {
        String sql = String.format("INSERT INTO appointments (`Title`, `Description`, `Location`, `Type`, `Start`, `End`, `Customer_ID`, `User_ID`, `Contact_ID`) VALUES ('%s',\"%s\",'%s','%s',TIMESTAMP '%s',TIMESTAMP '%s',%d,%d,%d)",
                title, description, location, type, startDateTime,  endDateTime, customerData.getCustomerID(), currentUser.getUserID(), contactId);
        System.out.println(sql);
        Query.makeQuery(sql);
    }

    /**
     * <p>Updates a row of appointment information within the database appointments table setting title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId</p>
     *
     * @param appointmentId the appointmentId to set
     * @param title the title to set
     * @param description the description to set
     * @param location the location to set
     * @param type the type to set
     * @param startDateTime the startDateTime to set
     * @param endDateTime the endDateTime to set
     * @param customerId the customerId to set
     * @param userId the userId to set
     * @param contactId the contactId to set
     * @throws SQLException
     */
    public static void updateAppointment(int appointmentId, String title, String description, String location, String type, Timestamp startDateTime, Timestamp endDateTime, int customerId, int userId, int contactId) throws SQLException {
        String sql = String.format("UPDATE appointments SET Title = %s, Description = \"%s\", Location = %s, Type = %s, Start = %s, End = %s, Contact_ID = %d, Customer_ID = %d, User_ID = %d WHERE Appointment_ID = %d",
                title, description, location, type, startDateTime, endDateTime, contactId, customerId, userId, appointmentId);
        Query.makeQuery(sql);
    }

    /**
     * <p>Deletes a row of appointment information within the database appointments table referencing set appointmentId</p>
     *
     * @param appointmentId  the appointmentId to set
     * @throws SQLException
     */
    public static void deleteAppointment(int appointmentId) throws SQLException {
            String sql = String.format("DELETE FROM appointments WHERE Appointment_ID=%d", appointmentId);
            Query.makeQuery(sql);
    }

    /**
     * <p>Returns an ObservableList of Appointment objects querying database appointments table.
     * The returned ObservableList objects are based on time period set dates</p>
     *
     * @param dates  the dates to set
     * @return allAppointments
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllAppointments(String dates) throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        if (dates.equals("week")) {
            String sqlStatement = String.format("SELECT * FROM appointments WHERE User_ID=%d AND YEARWEEK(Start) = YEARWEEK(CURDATE()) ORDER BY Start DESC",
                    currentUser.getUserID());
            Query.makeQuery(sqlStatement);
            ResultSet result = Query.getResult();
            while (result.next()) {
                allAppointments.add(queryAppointmentInfo(result));
            }
        } else if (dates.equals("month")) {
            String sqlStatement = String.format("SELECT * FROM appointments WHERE User_ID=%d AND (YEAR(Start) = YEAR(CURDATE()) AND MONTH(Start) = MONTH(CURDATE())) ORDER BY Start ASC",
                    currentUser.getUserID());
            Query.makeQuery(sqlStatement);
            ResultSet result = Query.getResult();
            while (result.next()) {
                allAppointments.add(queryAppointmentInfo(result));
            }
        } else {
            String sqlStatement = String.format("SELECT * FROM appointments WHERE User_ID=%d",
                    currentUser.getUserID());
            Query.makeQuery(sqlStatement);
            ResultSet result = Query.getResult();
            while (result.next()) {
                allAppointments.add(queryAppointmentInfo(result));
            }
        }

        return allAppointments;
    }

    private static Appointment queryAppointmentInfo(ResultSet result) throws SQLException {
        int appointmentId = result.getInt("Appointment_ID");
        String title = result.getString("Title");
        String description = result.getString("Description");
        String location = result.getString("Location");
        String type = result.getString("Type");
        Timestamp startDateTime = result.getTimestamp("Start");
        Timestamp endDateTime = result.getTimestamp("End");
        int contactId = result.getInt("Contact_ID");
        int customerId = result.getInt("Customer_ID");
        int userId = result.getInt("User_ID");
        return new Appointment(appointmentId, title, description, location, contactId, type, startDateTime, endDateTime, customerId, userId);
    }
}
