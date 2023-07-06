package com.nguyen.c195.DAO;

import com.nguyen.c195.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static com.nguyen.c195.helper.VerifyUser.loginID;

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
    public static void insertAppointment(String title, String description, String location, String type, Timestamp startDateTime, Timestamp endDateTime, int customerId, int userId, int contactId) throws SQLException {
        DBConnection.openConnection();
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startDateTime);
        ps.setTimestamp(6, endDateTime);
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        ps.executeUpdate();
        DBConnection.closeConnection();
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
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Contact_ID = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startDateTime);
            ps.setTimestamp(6, endDateTime);
            ps.setInt(7, contactId);
            ps.setInt(8, customerId);
            ps.setInt(9, userId);
            ps.setInt(10, appointmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Deletes a row of appointment information within the database appointments table referencing set appointmentId</p>
     *
     * @param appointmentId  the appointmentId to set
     * @throws SQLException
     */
    public static void deleteAppointment(int appointmentId) throws SQLException {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID=?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ps.executeUpdate();
        } catch (SQLException e) {

        }
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
            String sqlStatement = "SELECT * FROM appointments WHERE User_ID=? AND YEARWEEK(Start) = YEARWEEK(CURDATE()) ORDER BY Start DESC";
            //Query.makeQuery(sqlStatement);
            PreparedStatement ps = DBConnection.connection.prepareStatement(sqlStatement);
            ps.setInt(1, loginID);
            ps.executeQuery();
            ResultSet result = ps.getResultSet();
            while (result.next()) {
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

                Appointment appointmentResult = new Appointment(appointmentId, title, description, location, ContactDAOImpl.getContactByID(contactId), type, startDateTime, endDateTime, customerId, userId);
                allAppointments.add(appointmentResult);
            }
        } else if (dates.equals("month")) {
            String sqlStatement = "SELECT * FROM appointments WHERE User_ID=? AND (YEAR(Start) = YEAR(CURDATE()) AND MONTH(Start) = MONTH(CURDATE())) ORDER BY Start ASC";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sqlStatement);
            ps.setInt(1, loginID);
            ps.executeQuery();
            ResultSet result = ps.getResultSet();
            while (result.next()) {
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

                Appointment appointmentResult = new Appointment(appointmentId, title, description, location, ContactDAOImpl.getContactByID(contactId), type, startDateTime, endDateTime, customerId, userId);
                allAppointments.add(appointmentResult);
            }
        } else {
            String sqlStatement = "SELECT * FROM appointments WHERE User_ID=?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sqlStatement);
            ps.setInt(1, loginID);
            ps.executeQuery();
            ResultSet result = ps.getResultSet();
            while (result.next()) {
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

                Appointment appointmentResult = new Appointment(appointmentId, title, description, location, ContactDAOImpl.getContactByID(contactId), type, startDateTime, endDateTime, customerId, userId);
                allAppointments.add(appointmentResult);
            }
        }

        return allAppointments;
    }
}
