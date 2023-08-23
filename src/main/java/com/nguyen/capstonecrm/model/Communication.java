package com.nguyen.capstonecrm.model;

import java.time.LocalDate;

/**
 *Model for communicaton object
 */
public class Communication extends CustInfo {

    /**
     * @param custInfoID
     * @param type
     * @param desc
     * @param when
     * @param custID
     */
    public Communication(int custInfoID, String type, String desc, LocalDate when, int custID) {
        super(custInfoID, type, desc, when, custID);
    }

}
