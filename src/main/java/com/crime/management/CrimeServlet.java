package com.crime.management;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class CrimeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/dbms_project";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "@Anavatti123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Add a new crime
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String type = request.getParameter("type");
        String time = request.getParameter("time");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String streetName = request.getParameter("streetName");
        String evidence = request.getParameter("evidence");
        String victims = request.getParameter("victims");
        String criminalID = request.getParameter("criminalID");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO crime (type, time, address, city, streetName, criminalID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, type);
            stmt.setString(2, time);
            stmt.setString(3, address);
            stmt.setString(4, city);
            stmt.setString(5, streetName);
            stmt.setString(6, criminalID);

            int result = stmt.executeUpdate();

            if (result > 0) {
                out.println("<h3>Crime Added Successfully</h3>");
            } else {
                out.println("<h3>Error Adding Crime</h3>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Database Error: " + e.getMessage() + "</h3>");
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Update an existing crime
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String crimeID = request.getParameter("crimeID");
        String type = request.getParameter("type");
        String time = request.getParameter("time");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String streetName = request.getParameter("streetName");
        String criminalID = request.getParameter("criminalID");
        String judgeID = request.getParameter("judgeID");
        String officerID = request.getParameter("officerID");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "UPDATE crime SET type=?, time=?, address=?, city=?, streetName=?, criminalID=?, judgeID=?, officerID=? WHERE crimeID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, type);
            stmt.setString(2, time);
            stmt.setString(3, address);
            stmt.setString(4, city);
            stmt.setString(5, streetName);
            stmt.setString(6, criminalID);
            stmt.setString(7, judgeID);
            stmt.setString(8, officerID);
            stmt.setString(9, crimeID);

            int result = stmt.executeUpdate();

            if (result > 0) {
                out.println("<h3>Crime Updated Successfully</h3>");
            } else {
                out.println("<h3>Error Updating Crime</h3>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Database Error: " + e.getMessage() + "</h3>");
        }
    }
}
