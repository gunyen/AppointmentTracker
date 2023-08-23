package com.nguyen.capstonecrm.model;

/**
 *Model for user object
 */
public class User extends Person{

    private int userID;
    private String password;


    /**
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param email
     * @param divisionID
     * @param userID
     * @param password
     */
    public User(String name, String address, String postalCode, String phone, String email, int divisionID, int userID, String password) {
        super(name, address, postalCode, phone, email, divisionID);
        this.userID = userID;
        this.password = password;
    }

    /**
     * @return
     */
    public int getUserID() {
        return userID;
    }


    /**
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }


    /**
     * @return
     */
    public String getPassword() {
        return password;
    }


    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
