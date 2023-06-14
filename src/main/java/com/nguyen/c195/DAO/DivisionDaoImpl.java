package com.nguyen.c195.DAO;/*
C195
Created by: John Nguyen
Creation Date: 5/31/2023
Creation Time: 7:02 PM
*/

import com.nguyen.c195.model.Division;

public abstract class DivisionDaoImpl {
    public static Division getDivision(String division) throws Exception {
        DBConnection.openConnection();

        DBConnection.closeConnection();
        return null;
    }

}
