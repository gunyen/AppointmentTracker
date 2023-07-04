package com.nguyen.c195.DAO;/*
C195
Created by: John Nguyen
Creation Date: 5/31/2023
Creation Time: 7:02 PM
*/

import com.nguyen.c195.model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Division Data Access Object
 */
public abstract class DivisionDaoImpl {
    
    /**
     * <p>Returns a Division object based on set divisionId when querying the database</p>
     *
     * @param divisionId the divisionId to set
     * @return Division
     * @throws Exception
     */
    public static Division getDivision(int divisionId) throws Exception {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            int divId = rs.getInt("Division_ID");
            String divName = rs.getString("Division");
            return new Division(divId, divName);
        }
        return null;
    }


}
