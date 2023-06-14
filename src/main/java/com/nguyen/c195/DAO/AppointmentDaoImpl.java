package com.nguyen.c195.DAO;/*
C195
Created by: John Nguyen
Creation Date: 5/31/2023
Creation Time: 7:03 PM
*/

import com.nguyen.c195.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static com.nguyen.c195.helper.VerifyUser.loginID;

public abstract class AppointmentDaoImpl {
    public static Appointment getAppointment(String title) throws Exception {
        DBConnection.openConnection();

        DBConnection.closeConnection();
        return null;
    }

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments= FXCollections.observableArrayList();
        //DBConnection.openConnection();
        String sqlStatement="SELECT appointments.*, contacts.Contact_Name FROM appointments JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID WHERE User_ID=?";
        //Query.makeQuery(sqlStatement);
        PreparedStatement ps = DBConnection.connection.prepareStatement(sqlStatement);
        ps.setInt(1, loginID);
        ps.executeQuery();
        ResultSet result= ps.getResultSet();
        while(result.next()){
            int appointmentId = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String location = result.getString("Location");
            String contact = result.getString("Contact_Name");
            String type = result.getString("Type");
            Timestamp startDateTime = result.getTimestamp("Start");
            Timestamp endDateTime = result.getTimestamp("End");
            int customerId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            Appointment appointmentResult= new Appointment(appointmentId, title, description, location, contact, type, startDateTime, endDateTime, customerId,userId);
            allAppointments.add(appointmentResult);
        }
        DBConnection.closeConnection();
        return allAppointments;
    }

}
