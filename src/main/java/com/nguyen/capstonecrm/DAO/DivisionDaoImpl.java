package com.nguyen.capstonecrm.DAO;

import com.nguyen.capstonecrm.model.Division;

import java.sql.ResultSet;

/**
 * Division Data Access Object
 */
public abstract class DivisionDaoImpl {

    /**
     * @param divisionId
     * @return
     * @throws Exception
     */
    public static Division getDivision(int divisionId) throws Exception {
        String sql = String.format("SELECT * FROM first_level_divisions WHERE Division_ID = %d", divisionId);
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        while (rs.next()) {
            int divId = rs.getInt("Division_ID");
            String divName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            return new Division(divId, divName, countryID);
        }
        return null;
    }


}
