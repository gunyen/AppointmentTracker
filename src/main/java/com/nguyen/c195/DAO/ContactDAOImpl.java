package com.nguyen.c195.DAO;

import com.nguyen.c195.model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contact Data Access Object
 */
public abstract class ContactDAOImpl {

    /**
     * <p>Returns a Contact object with set contactId as reference when querying the database contacts table</p>
     *
     * @param contactId  the contactId to set
     * @return Contacts
     * @throws SQLException
     */
    public static Contacts getContactByID(int contactId) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_ID=?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            return new Contacts(contactID, contactName, email);
        }
        return null;
    }

    /**
     * <p>Returns an ObservableList of Contacts objects querying the database contacts table</p>
     *
     * @return allContacts
     * @throws Exception
     */
    public static ObservableList<Contacts> getAllContacts() throws Exception {
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM contacts";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int contactId = result.getInt("Contact_ID");
            String contactName = result.getString("Contact_Name");
            String email = result.getString("Email");
            Contacts contactsResult = new Contacts(contactId, contactName, email);
            allContacts.add(contactsResult);
        }
        return allContacts;
    }
}
