package com.nguyen.capstonecrm.model;

/**
 * Models the creation of Division objects
 */
public class Division extends Geo{

    private int countryID;

    /**
     * @param geoID
     * @param geoName
     * @param countryID
     */
    public Division(int geoID, String geoName, int countryID) {
        super(geoID, geoName);
        this.countryID = countryID;
    }

    /**
     * @return the division
     */
    @Override
    public String toString() {
        return (getGeoName());
    }

    /**
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
