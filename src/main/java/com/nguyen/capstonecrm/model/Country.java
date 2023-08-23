package com.nguyen.capstonecrm.model;

/**
 * Models the creation of Country objects
 */
public class Country extends Geo{

    /**
     * @param geoID
     * @param geoName
     */
    public Country(int geoID, String geoName) {
        super(geoID, geoName);
    }


    /**
     * @return
     */
    @Override
    public String toString() {
        return (getGeoName());
    }



}
