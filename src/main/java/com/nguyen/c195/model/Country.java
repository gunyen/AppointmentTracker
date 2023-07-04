package com.nguyen.c195.model;/*
C195
Created by: John Nguyen
Creation Date: 5/31/2023
Creation Time: 7:02 PM
*/

/**
 * Models the creation of Country objects
 */
public class Country {
    private int countryId;
    private String country;

    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * @return the country
     */
    @Override
    public String toString() {
        return (country);
    }

    /**
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

}
