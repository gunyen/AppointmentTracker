package com.nguyen.capstonecrm.DAO;

import com.nguyen.capstonecrm.model.Worklog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *Data Access Object for Worklog data
 */
public class WorklogDaoImpl implements CRUD {
    /**
     * @param type
     * @param desc
     * @param when
     * @param status
     * @param custID
     */
    public static void insert(String type, String desc, String when, String status, int custID) {
        String sql = String.format("INSERT INTO worklogs (Type,Description,Status,Created,Customer_ID) VALUES ('%s','%s','%s',DATE '%s',%d)",
                type, desc, status, when, custID);
        Query.makeQuery(sql);
    }

    /**
     * @param custInfoID
     * @param type
     * @param desc
     * @param when
     * @param status
     */
    public static void update(int custInfoID, String type, String desc, String when, String status) {
        String sql = String.format("UPDATE worklogs SET `Type`='%s',`Description`='%s',`Status`='%s',`Created`=(DATE '%s') WHERE `Log_ID`=%d",
                type, desc, status, when, custInfoID);
        Query.makeQuery(sql);
    }

    /**
     * @param id
     */
    public static void delete(int id) {
        String sql = String.format("DELETE FROM worklogs WHERE Log_ID=%d",
                id);
        Query.makeQuery(sql);
    }

    /**
     * @param id
     * @return
     * @throws SQLException
     */
    public static Worklog getWorklog(int id) throws SQLException {
        String sql = String.format("SELECT * FROM worklogs WHERE Log_ID  = '%s'",
                id);
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        while (rs.next()) {
            return queryWorklogInfo(rs);
        }
        return null;
    }

    /**
     * @param filter
     * @param custID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Worklog> getAllWorklogs(int filter,int custID) throws SQLException {
        ObservableList<Worklog> allWorklogs = FXCollections.observableArrayList();
        if(filter==1){
            String sql = String.format("SELECT * FROM worklogs WHERE Customer_ID=%d",custID);
            Query.makeQuery(sql);
            ResultSet rs = Query.getResult();
            while(rs.next()){
                allWorklogs.add(queryWorklogInfo(rs));
            }
        } else if (filter==2) {
            String sql = String.format("SELECT * FROM worklogs WHERE Status='Open' AND Customer_ID=%d",custID);
            Query.makeQuery(sql);
            ResultSet rs = Query.getResult();
            while(rs.next()){
                allWorklogs.add(queryWorklogInfo(rs));
            }
        } else if (filter==3) {
            String sql = String.format("SELECT * FROM worklogs WHERE Status='Closed' AND Customer_ID=%d",custID);
            Query.makeQuery(sql);
            ResultSet rs = Query.getResult();
            while(rs.next()){
                allWorklogs.add(queryWorklogInfo(rs));
            }
        }

        return allWorklogs;
    }

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    public static Worklog queryWorklogInfo(ResultSet rs) throws SQLException {
        int custInfoID = rs.getInt("Log_ID");
        String type = rs.getString("Type");
        String desc = rs.getString("Description");
        LocalDate when =rs.getDate("Created").toLocalDate();
        int custID = rs.getInt("Customer_ID");
        String status = rs.getString("Status");
        return new Worklog(custInfoID, type, desc, when, custID, status);
    }
}
