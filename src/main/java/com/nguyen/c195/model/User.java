package com.nguyen.c195.model;/*
C195
Created by: John Nguyen
Creation Date: 5/27/2023
Creation Time: 4:45 PM
*/

/**
 * Models the creation of User objects
 */
public class User {
    private int userID;
    private String userName;
    private String password;

    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userID;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userID = userId;
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
