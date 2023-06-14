package com.nguyen.c195.model;/*
C195
Created by: John Nguyen
Creation Date: 6/9/2023
Creation Time: 4:13 PM
*/

public class Contact {
    private int contactId;
    private String contactName;
    private String contactEmail;

    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return (contactName);
    }

    public Contact(String contactName) {
        this.contactName = contactName;
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
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
