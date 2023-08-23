package com.nguyen.capstonecrm.model;

import com.nguyen.capstonecrm.DAO.ContactDAOImpl;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Models the creation of Appointment objects
 */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private Contacts contacts;
    private int contactId;
    private String type;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private int customerId;
    private int userId;


    /**
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param contactId
     * @param type
     * @param startDateTime
     * @param endDateTime
     * @param customerId
     * @param userId
     * @throws SQLException
     */
    public Appointment(int appointmentId, String title, String description, String location, int contactId, String type, Timestamp startDateTime, Timestamp endDateTime, int customerId, int userId) throws SQLException {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.contacts = ContactDAOImpl.getContactByID(contactId);
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
    }

    /**
     * @return
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return
     */
    public Contacts getContacts() {
        return contacts;
    }

    /**
     * @param contacts
     */
    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    /**
     * @return
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return
     */
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    /**
     * @param startDateTime
     */
    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * @return
     */
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    /**
     * @param endDateTime
     */
    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
