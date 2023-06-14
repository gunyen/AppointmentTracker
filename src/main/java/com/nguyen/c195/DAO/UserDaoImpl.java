package com.nguyen.c195.DAO;/*
C195
Created by: John Nguyen
Creation Date: 5/27/2023
Creation Time: 4:32 PM
*/

import com.nguyen.c195.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public abstract class UserDaoImpl {

    public static User getUser(String userName) throws Exception{
        // type is name or phone, value is the name or the phone #
        DBConnection.openConnection();
        String sqlStatement="SELECT * FROM users WHERE User_Name  = '" + userName + "'";
        //  String sqlStatement="select FROM address";
        Query.makeQuery(sqlStatement);
        User userResult;
        ResultSet result=Query.getResult();
        while(result.next()){
            int userid=result.getInt("User_ID");
            String password=result.getString("Password");
            userResult= new User(userid, userName, password);
            return userResult;
        }
        DBConnection.closeConnection();
        return null;
    }

    public static ObservableList<User> getAllUsers() throws Exception{
        ObservableList<User> allUsers= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="SELECT * FROM users";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int userid=result.getInt("User_ID");
            String userName=result.getString("User_Name");
            String password=result.getString("Password");
            User userResult= new User(userid, userName, password);
            allUsers.add(userResult);
        }
        DBConnection.closeConnection();
        return allUsers;
    }

}