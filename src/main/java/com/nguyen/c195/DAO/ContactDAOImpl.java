package com.nguyen.c195.DAO;/*
C195
Created by: John Nguyen
Creation Date: 6/13/2023
Creation Time: 6:05 PM
*/

import com.nguyen.c195.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public abstract class ContactDAOImpl {
    public static ObservableList<Contact> getAllContacts() throws Exception{
        ObservableList<Contact> allContacts= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="SELECT * FROM contacts";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int contactId=result.getInt("Contact_ID");
            String contactName=result.getString("Contact_Name");
            String email=result.getString("Email");
            Contact contactResult= new Contact(contactId, contactName, email);
            allContacts.add(contactResult);
        }
        DBConnection.closeConnection();
        return allContacts;
    }
}
