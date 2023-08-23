package com.nguyen.capstonecrm.helper;

import com.nguyen.capstonecrm.DAO.Query;
import com.nguyen.capstonecrm.model.Country;
import com.nguyen.capstonecrm.model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles operations between Country and Division objects
 */
public abstract class CountryDivOrg {

    /**
     * <p>Returns an ObservableList of Division objects based on set countryId</p>
     *
     * @param countryId the countryId to set
     * @return allDivisions
     * @throws Exception
     */
    public static ObservableList<Division> getDivisionsByCountry(int countryId) throws Exception {
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        String sql = String.format("SELECT * FROM first_level_divisions  WHERE Country_ID = %d",
                countryId);
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            Division division = new Division(divisionId, divisionName, countryID);
            allDivisions.add(division);
        }
        return allDivisions;
    }

    /**
     * <p>Returns a Country object based on a set divisionId</p>
     *
     * @param divisionId the divisionId to set
     * @return country or null
     * @throws SQLException
     */
    public static Country getCountryByDivisionID(int divisionId) throws SQLException {
        String sql = String.format("SELECT countries.*, first_level_divisions.* FROM first_level_divisions JOIN countries ON countries.Country_ID=first_level_divisions.Country_ID WHERE Division_ID=%d",
                divisionId);
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();
        while (rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country country = new Country(countryId, countryName);
            return country;
        }
        return null;
    }

}
