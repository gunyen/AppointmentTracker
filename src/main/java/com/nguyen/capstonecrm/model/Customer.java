package com.nguyen.capstonecrm.model;

import com.nguyen.capstonecrm.DAO.CustomerDaoImpl;

import java.sql.SQLException;

/**
 *Model of the customer object
 */
public class Customer extends Person{
    private int customerID;

    /**
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param email
     * @param divisionID
     * @param customerID
     */
    public Customer(String name, String address, String postalCode, String phone, String email, int divisionID, int customerID) {
        super(name, address, postalCode, phone, email, divisionID);
        this.customerID = customerID;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return (customerID + ": " + getName());
    }

    /**
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @param name
     * @return
     * @throws SQLException
     */
    public static Customer lookupCustomerName(String name) throws SQLException {
        for (Customer customer : CustomerDaoImpl.getAllCustomers()) {
            if (customer.getName().contains(name)) {
                return customer;
            }
        }
        return null;
    }
}
