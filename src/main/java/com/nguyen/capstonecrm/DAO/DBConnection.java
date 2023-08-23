package com.nguyen.capstonecrm.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Tunnel to connect database
 */
public abstract class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//165.1.74.179/";
    private static final String databaseName = "test";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "mywgu";
    private static final String password = "mywgu";

    public static Connection connection;

    /**
     * <p>Opens a connection to the database</p>
     */
    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * <p>Closes any open connections to the database</p>
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection Closed");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        }
    }
}

