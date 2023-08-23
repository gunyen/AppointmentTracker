package com.nguyen.capstonecrm.DAO;

import com.nguyen.capstonecrm.model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contact Data Access Object
 */
public abstract class ContactDAOImpl {

    /**
     * @param contactId
     * @return
     * @throws SQLException
     */
    public static Contacts getContactByID(int contactId) throws SQLException {
        String sql = String.format("SELECT * FROM contacts WHERE Contact_ID=%d", contactId);
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        while (rs.next()) {
            return queryContactInfo(rs);
        }
        return null;
    }

    /**
     * @return
     * @throws Exception
     */
    public static ObservableList<Contacts> getAllContacts() throws Exception {
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM contacts";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while (result.next()) {
            allContacts.add(queryContactInfo(result));
        }
        return allContacts;
    }

    private static Contacts queryContactInfo(ResultSet result) throws SQLException {
        int contactId = result.getInt("Contact_ID");
        String contactName = result.getString("Contact_Name");
        String email = result.getString("Email");
        return new Contacts(contactId, contactName, email);
    }
}
