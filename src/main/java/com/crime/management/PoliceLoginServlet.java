package com.crime.management;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class PoliceLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String officerID = request.getParameter("officerID");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms_project", "root", "@Anavatti123");

            String sql = "SELECT * FROM police WHERE officerID = ? AND password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, officerID);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Successful login: store officer session
                HttpSession session = request.getSession();
                session.setAttribute("officerID", officerID);
                response.sendRedirect("PoliceServlet"); // Redirect to the protected police page
            } else {
                response.sendRedirect("police-login.html?error=invalid");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("police-login.html?error=server");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }
}
