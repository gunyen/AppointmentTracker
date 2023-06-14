package com.nguyen.c195.DAO;/*
C195
Created by: John Nguyen
Creation Date: 5/27/2023
Creation Time: 4:04 PM
*/

import java.sql.ResultSet;
import java.sql.Statement;

import static com.nguyen.c195.DAO.DBConnection.connection;

public abstract class Query {
    private static String query;
    private static Statement stmt;
    private static ResultSet result;

    public static void makeQuery(String q){
        query =q;
        try{
            stmt=connection.createStatement();
            // determine query execution
            if(query.toLowerCase().startsWith("select"))
                result=stmt.executeQuery(q);
            if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(q);

        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
    public static ResultSet getResult(){
        return result;
    }
}
