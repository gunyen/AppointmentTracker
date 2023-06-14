package com.nguyen.c195.model;/*
C195
Created by: John Nguyen
Creation Date: 5/31/2023
Creation Time: 7:02 PM
*/

public class Division {
    private int divisionId;
    private String division;

    public Division(int divisionId, String division) {
        this.divisionId = divisionId;
        this.division = division;
    }

    /**
     * @return
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }
}
