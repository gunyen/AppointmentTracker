package com.nguyen.capstonecrm.model;

/**
 * Models the creation of Contacts objects
 */
public class Contacts{
    private int contactId;
    private String contactName;
    private String contactEmail;

    /**
     * @param contactId
     * @param contactName
     * @param contactEmail
     */
    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * @param contactName
     * @param contactEmail
     */
    public Contacts(String contactName, String contactEmail) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * @param contactName
     */
    public Contacts(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return (contactName);
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
