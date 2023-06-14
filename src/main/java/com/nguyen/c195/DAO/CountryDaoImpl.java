package com.nguyen.c195.DAO;/*
C195
Created by: John Nguyen
Creation Date: 5/31/2023
Creation Time: 7:03 PM
*/

import com.nguyen.c195.model.Country;

public abstract class CountryDaoImpl {
    public static Country getCountry(String country) throws Exception {
        DBConnection.openConnection();

        DBConnection.closeConnection();
        return null;
    }
}
