package com.crime.management;

import com.crime.management.*;
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


@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dbms_project";  // Change DB name if needed
    private static final String DB_USER = "root";  // Change as per your DB config
    private static final String DB_PASSWORD = "@Anavatti123";  // Change as per your DB config

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String role = request.getParameter("role");
        String action = request.getParameter("action"); // add, update, delete

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = null;

            if ("police".equals(role)) {
                String officerID = request.getParameter("officerID");
                String name = request.getParameter("name");
                String station = request.getParameter("station");
                String contact = request.getParameter("contact");
                String password = request.getParameter("password");

                if ("add".equals(action)) {
                    pstmt = conn.prepareStatement("INSERT INTO police (officerID, name, station, contact, password) VALUES (?, ?, ?, ?, ?)");
                    pstmt.setString(1, officerID);
                    pstmt.setString(2, name);
                    pstmt.setString(3, station);
                    pstmt.setString(4, contact);
                    pstmt.setString(5, password);
                } else if ("update".equals(action)) {
                    pstmt = conn.prepareStatement("UPDATE police SET name=?, station=?, contact=?, password=? WHERE officerID=?");
                    pstmt.setString(1, name);
                    pstmt.setString(2, station);
                    pstmt.setString(3, contact);
                    pstmt.setString(4, password);
                    pstmt.setString(5, officerID);
                } else if ("delete".equals(action)) {
                    pstmt = conn.prepareStatement("DELETE FROM police WHERE officerID=?");
                    pstmt.setString(1, officerID);
                }
            } else if ("judge".equals(role)) {
                String judgeID = request.getParameter("judgeID");
                String name = request.getParameter("name");
                String court = request.getParameter("court");
                String password = request.getParameter("password");

                if ("add".equals(action)) {
                    pstmt = conn.prepareStatement("INSERT INTO judge (judgeID, name, court, password) VALUES (?, ?, ?, ?)");
                    pstmt.setString(1, judgeID);
                    pstmt.setString(2, name);
                    pstmt.setString(3, court);
                    pstmt.setString(4, password);
                } else if ("update".equals(action)) {
                    pstmt = conn.prepareStatement("UPDATE judge SET name=?, court=?, password=? WHERE judgeID=?");
                    pstmt.setString(1, name);
                    pstmt.setString(2, court);
                    pstmt.setString(3, password);
                    pstmt.setString(4, judgeID);
                } else if ("delete".equals(action)) {
                    pstmt = conn.prepareStatement("DELETE FROM judge WHERE judgeID=?");
                    pstmt.setString(1, judgeID);
                }
            }

            if (pstmt != null) {
                int result = pstmt.executeUpdate();
                if (result > 0) {
                    out.println("<script>alert('Operation successful!'); window.location='admin.html';</script>");
                } else {
                    out.println("<script>alert('Operation failed!'); window.location='admin.html';</script>");
                }
            }

            conn.close();
        } catch (Exception e) {
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='admin.html';</script>");
        }
    }
}
