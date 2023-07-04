package com.nguyen.c195.helper;/*
C195
Created by: John Nguyen
Creation Date: 6/18/2023
Creation Time: 7:51 PM
*/

import com.nguyen.c195.DAO.DBConnection;
import com.nguyen.c195.model.Country;
import com.nguyen.c195.model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
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
        String sql = "SELECT * FROM first_level_divisions  WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            Division division = new Division(divisionId, divisionName);
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
        String sql = "SELECT countries.*, first_level_divisions.* FROM first_level_divisions JOIN countries ON countries.Country_ID=first_level_divisions.Country_ID WHERE Division_ID=?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country country = new Country(countryId, countryName);
            return country;
        }
        return null;
    }

}
