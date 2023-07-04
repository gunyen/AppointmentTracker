package com.nguyen.c195.model;/*
C195
Created by: John Nguyen
Creation Date: 6/29/2023
Creation Time: 11:18 PM
*/

import java.sql.Timestamp;
import java.time.Month;

/**
 * Models the creation of Report objects
 */
public class Report {

    private String type;
    private Month month2;
    private int total;
    private int contactID;
    private Contacts contacts;
    private int appointmentId;
    private String title;
    private String description;
    private Timestamp starts;
    private Timestamp ends;
    private Timestamp login;
    private int customerID;
    private int userId;
    private String userName;
    private String success;

    public Report(String type, Month month2, int total, int contactID, Contacts contacts, int appointmentId, String title, String description, Timestamp starts, Timestamp ends, Timestamp login, int customerID, int userId, String userName, String success) {
        this.type = type;
        this.month2 = month2;
        this.total = total;
        this.contactID = contactID;
        this.contacts = contacts;
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.starts = starts;
        this.ends = ends;
        this.login = login;
        this.customerID = customerID;
        this.userId = userId;
        this.userName = userName;
        this.success = success;
    }

    public Report(String type, Contacts contacts, int appointmentId, String title, String description, Timestamp starts, Timestamp ends, int customerID) {
        this.type = type;
        this.contacts = contacts;
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.starts = starts;
        this.ends = ends;
        this.customerID = customerID;
    }

    public Report(String type, Month month2, int total) {
        this.type = type;
        this.month2 = month2;
        this.total = total;
    }

    public Report(String userName, Month month2, Timestamp login, String success) {
        this.userName = userName;
        this.month2 = month2;
        this.login = login;
        this.success = success;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return month2
     */
    public Month getMonth2() {
        return month2;
    }

    /**
     * @param month2 the month2 to set
     */
    public void setMonth2(Month month2) {
        this.month2 = month2;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return the contactId
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * @param contactID the contactId to set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * @return the contacts
     */
    public Contacts getContacts() {
        return contacts;
    }

    /**
     * @param contacts the contacts to set
     */
    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    /**
     * @return the appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @param appointmentId the appointmentId to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the starts
     */
    public Timestamp getStarts() {
        return starts;
    }

    /**
     * @param starts the starts to set
     */
    public void setStarts(Timestamp starts) {
        this.starts = starts;
    }

    /**
     * @return the ends
     */
    public Timestamp getEnds() {
        return ends;
    }

    /**
     * @param ends the ends to set
     */
    public void setEnds(Timestamp ends) {
        this.ends = ends;
    }

    /**
     * @return login
     */
    public Timestamp getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(Timestamp login) {
        this.login = login;
    }

    /**
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the success
     */
    public String getSuccess() {
        return success;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(String success) {
        this.success = success;
    }
}
