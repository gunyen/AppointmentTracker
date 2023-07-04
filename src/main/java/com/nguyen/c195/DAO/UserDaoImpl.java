package com.nguyen.c195.DAO;/*
C195
Created by: John Nguyen
Creation Date: 5/27/2023
Creation Time: 4:32 PM
*/

import com.nguyen.c195.model.User;

import java.sql.ResultSet;

/**
 * User Data Access Object
 */
public abstract class UserDaoImpl {

    /**
     * Returns a User object using set userName as reference to query the database
     *
     * @param userName the userName to set
     * @return userResult or null
     * @throws Exception
     */
    public static User getUser(String userName) throws Exception {
        String sqlStatement = "SELECT * FROM users WHERE User_Name  = '" + userName + "'";
        Query.makeQuery(sqlStatement);
        User userResult;
        ResultSet result = Query.getResult();
        while (result.next()) {
            int userid = result.getInt("User_ID");
            String password = result.getString("Password");
            userResult = new User(userid, userName, password);
            return userResult;
        }
        return null;
    }

}