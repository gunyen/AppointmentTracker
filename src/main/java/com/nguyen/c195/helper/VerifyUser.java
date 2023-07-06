package com.nguyen.c195.helper;

import com.nguyen.c195.DAO.DBConnection;
import com.nguyen.c195.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Verifies end user data input on login.fxml
 */
public abstract class VerifyUser {
    public static int loginID;

    /**
     * <p>Returns a boolean variable that depends on whether the set userName and password match any row within the database table users</p>
     *
     * @param userName the userName to set
     * @param password the password to set
     * @return boolean value
     * @throws SQLException
     */
    public static boolean getUserPassword(String userName, String password) throws SQLException {
        try {
            String sql = "SELECT Password, User_ID FROM users WHERE User_Name = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                loginID = rs.getInt("User_ID");
                String pass = rs.getString("Password");
                User user = new User(userName, pass);
                if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
