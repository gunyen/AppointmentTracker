package com.nguyen.capstonecrm.DAO;

import com.nguyen.capstonecrm.model.Communication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *Communication Data Access Object
 */
public class CommunicationDaoImpl implements CRUD{
    /**
     * @param type
     * @param desc
     * @param when
     * @param custInfoID
     */
    public static void insert(String type, String desc, String when, int custInfoID) {
            String sql = String.format("INSERT INTO communications (`Type`,`Description`,`When`,`Customer_ID`) VALUES ('%s','%s',DATE '%s',%d)",
                type, desc, when, custInfoID);
        Query.makeQuery(sql);
    }

    /**
     * @param custInfoID
     * @param type
     * @param desc
     * @param when
     */
    public static void update(int custInfoID, String type, String desc, String when) {
        String sql = String.format("UPDATE communications SET `Type`='%s',`Description`='%s',`When`=DATE '%s' WHERE `Communication_ID`=%d",
                type, desc, when, custInfoID);
        Query.makeQuery(sql);
    }

    /**
     * @param id
     */
    public static void delete(int id) {
        String sql = String.format("DELETE FROM communications WHERE Communication_ID=%d",
                id);
        Query.makeQuery(sql);
    }

    /**
     * @param id
     * @return
     * @throws SQLException
     */
    public static Communication getCommunication(int id) throws SQLException {
        String sql = String.format("SELECT * FROM communications WHERE Communication_ID  = '%s'",
                id);
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        while (rs.next()) {
            return queryCommunicationInfo(rs);
        }
        return null;
    }

    /**
     * @param custID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Communication> getAllCommunications(int custID) throws SQLException {
        ObservableList<Communication> allCommunications = FXCollections.observableArrayList();
        String sql = String.format("SELECT * FROM communications WHERE Customer_ID='%d'",custID);
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        while(rs.next()){
            allCommunications.add(queryCommunicationInfo(rs));
        }
        return allCommunications;
    }

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    public static Communication queryCommunicationInfo(ResultSet rs) throws SQLException {
        int custInfoID = rs.getInt("Communication_ID");
        String type = rs.getString("Type");
        String desc = rs.getString("Description");
        LocalDate when =rs.getDate("When").toLocalDate();
        int custID = rs.getInt("Customer_ID");
        return new Communication(custInfoID, type, desc, when, custID);
    }
}
