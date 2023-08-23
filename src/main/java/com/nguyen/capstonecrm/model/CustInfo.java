package com.nguyen.capstonecrm.model;

import java.time.LocalDate;

/**
 *Abstract model for objects related to customer information
 */
public abstract class CustInfo {
    private int custInfoID;
    private String type;
    private String desc;
    private LocalDate when;
    private int custID;
    private static int interaction;

    /**
     * @param custInfoID
     * @param type
     * @param desc
     * @param when
     * @param custID
     */
    public CustInfo(int custInfoID, String type, String desc, LocalDate when, int custID) {
        this.custInfoID = custInfoID;
        this.type = type;
        this.desc = desc;
        this.when = when;
        this.custID = custID;
    }

    /**
     * @return
     */
    public int getCustInfoID() {
        return custInfoID;
    }

    /**
     * @param custInfoID
     */
    public void setCustInfoID(int custInfoID) {
        this.custInfoID = custInfoID;
    }

    /**
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return
     */
    public LocalDate getWhen() {
        return when;
    }

    /**
     * @param when
     */
    public void setWhen(LocalDate when) {
        this.when = when;
    }

    /**
     * @return
     */
    public int getCustID() {
        return custID;
    }

    /**
     * @param custID
     */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /**
     * @return
     */
    public static int getInteraction() {
        return interaction;
    }

    /**
     * @param interaction
     */
    public static void setInteraction(int interaction) {
        CustInfo.interaction = interaction;
    }
}
