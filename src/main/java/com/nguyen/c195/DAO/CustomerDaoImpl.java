package com.nguyen.c195.DAO;/*
C195
Created by: John Nguyen
Creation Date: 5/31/2023
Creation Time: 7:02 PM
*/

import com.nguyen.c195.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomerDaoImpl {
    public static Customer getCustomer(String customerName) throws Exception {
        DBConnection.openConnection();

        DBConnection.closeConnection();
        return null;
    }

    public static ObservableList<Customer> getAllCustomers() throws SQLException{
        ObservableList<Customer> allCustomers= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="SELECT * FROM customers";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int customerId=result.getInt("Customer_ID");
            String customerName=result.getString("Customer_Name");
            String address=result.getString("Address");
            String postalCode=result.getString("Postal_Code");
            String phone=result.getString("Phone");
            int divisionId=result.getInt("Division_ID");
            Customer customerResult= new Customer(customerId, customerName, address, postalCode, phone, divisionId);
            allCustomers.add(customerResult);
        }
        DBConnection.closeConnection();
        return allCustomers;
    }
}
