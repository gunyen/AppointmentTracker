package com.nguyen.capstonecrm.DAO;

import java.sql.ResultSet;
import java.sql.Statement;

import static com.nguyen.capstonecrm.DAO.DBConnection.connection;

/**
 * Execute MySql CRUD operations
 */
public abstract class Query {
    private static String query;
    private static Statement stmt;
    private static ResultSet result;

    /**
     * @param q the q to set
     */
    public static void makeQuery(String q) {
        query = q;
        try {
            stmt = connection.createStatement();
            // determine query execution
            if (query.toLowerCase().startsWith("select"))
                result = stmt.executeQuery(q);
            if (query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(q);

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * @return the result
     */
    public static ResultSet getResult() {
        return result;
    }
}
