package com.crime.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/dbms_project";
    private static final String USER = "root";  // Change this to your MySQL username
    private static final String PASSWORD = "@Anavatti123";  // Change this to your MySQL password
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection initializeDatabase() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found!", e);
        }
    }
}
