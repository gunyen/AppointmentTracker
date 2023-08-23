package com.nguyen.capstonecrm.model;

import java.time.LocalDate;

/**
 *Model for worklog object
 */
public class Worklog extends CustInfo {

    private String status;


    /**
     * @param custInfoID
     * @param type
     * @param desc
     * @param when
     * @param custID
     * @param status
     */
    public Worklog(int custInfoID, String type, String desc, LocalDate when, int custID, String status) {
        super(custInfoID, type, desc, when, custID);
        this.status = status;
    }

    /**
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
