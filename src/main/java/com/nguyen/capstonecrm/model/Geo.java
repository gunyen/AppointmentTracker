package com.nguyen.capstonecrm.model;

/**
 *Abstract model for objects related to location
 */
public abstract class Geo {
    private int geoID;
    private String geoName;

    /**
     * @param geoID
     * @param geoName
     */
    public Geo(int geoID, String geoName) {
        this.geoID = geoID;
        this.geoName = geoName;
    }

    /**
     * @return
     */
    public int getGeoID() {
        return geoID;
    }

    /**
     * @param geoID
     */
    public void setGeoID(int geoID) {
        this.geoID = geoID;
    }

    /**
     * @return
     */
    public String getGeoName() {
        return geoName;
    }

    /**
     * @param geoName
     */
    public void setGeoName(String geoName) {
        this.geoName = geoName;
    }
}
