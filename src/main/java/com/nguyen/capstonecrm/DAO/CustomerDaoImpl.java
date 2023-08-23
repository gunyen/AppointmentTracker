package com.nguyen.capstonecrm.DAO;

import com.nguyen.capstonecrm.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Customer Data Access Object
 */
public abstract class CustomerDaoImpl implements CRUD{

    /**
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param email
     * @param divisionID
     * @throws SQLException
     */
    public static void insert(String name, String address, String postalCode, String phone, String email, int divisionID) throws SQLException {
        String sql = String.format("INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Email, Division_ID) VALUES ('%s','%s','%s','%s','%s',%d)",
                name, address, postalCode, phone, email, divisionID);
        Query.makeQuery(sql);
    }

    /**
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param email
     * @param divisionID
     * @param customerID
     * @throws SQLException
     */
    public static void update(String name, String address, String postalCode, String phone, String email, int divisionID, int customerID) throws SQLException {
        String sql = String.format("UPDATE customers SET Customer_Name='%s', Address='%s', Postal_Code='%s', Phone='%s', Email='%s', Division_ID=%d WHERE Customer_ID=%d",
                name, address, postalCode, phone, email, divisionID, customerID);
        Query.makeQuery(sql);
    }

    /**
     * @param id
     * @throws SQLException
     */
    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID=" + id;
        Query.makeQuery(sql);
    }

    /**
     * @param name
     * @return
     * @throws SQLException
     */
    public static Customer getCustomer(String name) throws SQLException {
        String sql = "SELECT * FROM customers WHERE Customer_Name=" + name;
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        while(rs.next()){
            return queryCustomerInfo(rs);
        }
        return null;
    }

    /**
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM customers";
        Query.makeQuery(sqlStatement);
        ResultSet rs = Query.getResult();
        while (rs.next()) {
            allCustomers.add(queryCustomerInfo(rs));
        }
        return allCustomers;
    }

    /**
     * @param like
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllCustomers(String like) throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStatement = String.format("SELECT * FROM customers WHERE CONTAINS('%s')", like);
        Query.makeQuery(sqlStatement);
        ResultSet rs = Query.getResult();
        while (rs.next()) {
            allCustomers.add(queryCustomerInfo(rs));
        }
        return allCustomers;
    }

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    public static Customer queryCustomerInfo(ResultSet rs) throws SQLException {
        int customerID = rs.getInt("Customer_ID");
        String name = rs.getString("Customer_Name");
        String address = rs.getString("Address");
        String postalCode = rs.getString("Postal_Code");
        String phone = rs.getString("Phone");
        String email = rs.getString("Email");
        int divisionID = rs.getInt("Division_ID");
        return new Customer(name, address, postalCode, phone, email, divisionID, customerID);
    }
}
