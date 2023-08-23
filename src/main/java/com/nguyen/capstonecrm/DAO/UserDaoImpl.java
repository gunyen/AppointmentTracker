package com.nguyen.capstonecrm.DAO;

import com.nguyen.capstonecrm.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *Data Access Object for Users data
 */
public class UserDaoImpl implements CRUD{
    /**
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param email
     * @param divisionID
     * @param password
     * @throws SQLException
     */
    public static void insert(String name, String address, String postalCode, String phone, String email, int divisionID, String password) throws SQLException {
        String sql = String.format("INSERT INTO users (User_Name,Password,Address,Postal_Code,Phone,Email) VALUES ('%s','%s','%s','%s','%s',%d,'%s')",
                name,address,postalCode,phone,email,divisionID,password);
        Query.makeQuery(sql);
    }

    /**
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param email
     * @param divisionID
     * @param userID
     * @param password
     * @throws SQLException
     */
    public static void update(String name, String address, String postalCode, int phone, String email, int divisionID, int userID, String password) throws SQLException {
        String sql = String.format("UPDATE users SET User_Name='%s', Password='%s', Address='%s', Postal_Code='%s', Phone='%s', Email='%s', Division_ID=%d WHERE User_ID= %d",
                name,password,address,postalCode,phone,email,divisionID,userID);
        Query.makeQuery(sql);
    }

    /**
     * @param id
     * @throws SQLException
     */
    public static void delete(int id) throws SQLException {
        String sql = String.format("DELETE FROM users WHERE User_ID=%d",
                id);
        Query.makeQuery(sql);
    }

    /**
     * @param userName
     * @return
     * @throws SQLException
     */
    public static User getUser(String userName) throws SQLException {
        String sql = String.format("SELECT * FROM users WHERE User_Name  = '%s'",
                userName);
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        while (rs.next()) {
            return queryUserInfo(rs);
        }
        return null;
    }

    /**
     * @return
     * @throws SQLException
     */
    public static ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        while(rs.next()){
            allUsers.add(queryUserInfo(rs));
        }
        return allUsers;
    }

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    public static User queryUserInfo(ResultSet rs) throws SQLException {
            int userID = rs.getInt("User_ID");
            String password = rs.getString("Password");
            String name = rs.getString("User_Name");
            String address =rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            String email = rs.getString("Email");
            int divisionID = rs.getInt("Division_ID");
            return new User(name, address, postalCode, phone, email, divisionID, userID, password);
    }
}
