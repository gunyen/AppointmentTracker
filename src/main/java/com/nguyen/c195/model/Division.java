package com.nguyen.c195.model;

/**
 * Models the creation of Division objects
 */
public class Division {
    private int divisionId;
    private String division;

    public Division(int divisionId, String division) {
        this.divisionId = divisionId;
        this.division = division;
    }

    /**
     * @return the division
     */
    @Override
    public String toString() {
        return (division);
    }

    /**
     * @return the divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }
}
