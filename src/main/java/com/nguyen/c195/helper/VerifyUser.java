package com.nguyen.c195.helper;/*
C195
Created by: John Nguyen
Creation Date: 6/6/2023
Creation Time: 8:53 PM
*/

import com.nguyen.c195.DAO.DBConnection;
import com.nguyen.c195.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class VerifyUser {
    public static int loginID;
    public static boolean getUserPassword(String userName, String password) throws SQLException {
        try {
            DBConnection.openConnection();
            String sql="SELECT Password, User_ID FROM users WHERE User_Name = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                loginID = rs.getInt("User_ID");
                String pass= rs.getString("Password");
                User user= new User(userName, pass);
                if(user.getUserName().equals(userName)&&user.getPassword().equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return false;
    }
}
