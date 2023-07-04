package com.nguyen.c195.DAO;/*
C195
Created by: John Nguyen
Creation Date: 5/31/2023
Creation Time: 7:02 PM
*/

import com.nguyen.c195.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Customer Data Access Object
 */
public abstract class CustomerDaoImpl {
    
    /**
     * <p>Inserts a row of customer information within the database customers table setting customerName, address, postalCode, phone, divisionId</p>
     *
     * @param customerName the customerName to set
     * @param address the address to set
     * @param postalCode the postalCode to set
     * @param phone the phone to set
     * @param divisionId the divisionId to set
     * @throws SQLException
     */
    public static void insertCustomer(String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        ps.executeUpdate();
    }

    /**
     * <p>Updates a row of customer information within the database customers table setting customerName, address, postalCode, phone, divisionId</p>
     *
     * @param customerName the customerName to set
     * @param address the address to set
     * @param postalCode the postalCode to set
     * @param phone the phone to set
     * @param divisionId the divisionId to set
     * @param customerId the customerId to set
     * @throws SQLException
     */
    public static void updateCustomer(String customerName, String address, String postalCode, String phone, int divisionId, int customerId) throws SQLException {
        String sql = "UPDATE customers SET (Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=?) WHERE Customer_ID=?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        ps.setInt(6, customerId);
        ps.executeUpdate();
    }

    /**
     * <p>Deletes a row of customer information within the database customer table referencing set customerId</p>
     *
     * @param customerId the contactId to set
     * @throws SQLException
     */
    public static void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID=?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }

    /**
     * <p>Returns an ObservableList of Customer objects querying database customers table</p>
     *
     * @return allCustomers
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM customers";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int customerId = result.getInt("Customer_ID");
            String customerName = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postalCode = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            int divisionId = result.getInt("Division_ID");
            Customer customerResult = new Customer(customerId, customerName, address, postalCode, phone, divisionId);
            allCustomers.add(customerResult);
        }
        return allCustomers;
    }


}
