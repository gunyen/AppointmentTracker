package com.nguyen.c195.model;/*
C195
Created by: John Nguyen
Creation Date: 6/9/2023
Creation Time: 4:13 PM
*/

/**
 * Models the creation of Contacts objects
 */
public class Contacts {
    private int contactId;
    private String contactName;
    private String contactEmail;

    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public Contacts(String contactName, String contactEmail) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }
    
    public Contacts(String contactName) {
        this.contactName = contactName;
    }
    
    /**
     * @return the contactName
     */
    @Override
    public String toString() {
        return (contactName);
    }

    /**
     * @return the contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param contactId the contactId to set
     
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * @return the contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName the contactName to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return the contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail the contactEmail to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
