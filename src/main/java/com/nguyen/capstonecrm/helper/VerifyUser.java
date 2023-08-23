package com.nguyen.capstonecrm.helper;

import com.nguyen.capstonecrm.DAO.Query;
import com.nguyen.capstonecrm.DAO.UserDaoImpl;
import com.nguyen.capstonecrm.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Verifies end user data input on login.fxml
 */
public abstract class VerifyUser {
    public static User currentUser;

    /**
     * <p>Returns a boolean variable that depends on whether the set userName and password match any row within the database table users</p>
     *
     * @param userName the userName to set
     * @param password the password to set
     * @return boolean value
     * @throws SQLException
     */
    public static boolean getUserPassword(String userName, String password) throws SQLException {
        String sql = String.format("SELECT * FROM users WHERE User_Name='%s'",userName);
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        while(rs.next()){
            currentUser = UserDaoImpl.queryUserInfo(rs);
            if(currentUser.getName().equals(userName)&&currentUser.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}
