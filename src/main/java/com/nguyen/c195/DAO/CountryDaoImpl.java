package com.nguyen.c195.DAO;/*
C195
Created by: John Nguyen
Creation Date: 5/31/2023
Creation Time: 7:03 PM
*/

import com.nguyen.c195.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Country Data Access Object
 */
public abstract class CountryDaoImpl {

    /**
     * <p>Returns an ObservableList of Country objects when querying the database countries table</p>
     *
     * @return allCountries
     * @throws SQLException
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");

            Country country = new Country(countryId, countryName);
            allCountries.add(country);

        }
        return allCountries;
    }
}
