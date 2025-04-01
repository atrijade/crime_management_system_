package com.crime.management;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject jsonResponse = new JSONObject();

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms_project", "root", "@Anavatti123");

            // Queries to count records
            jsonResponse.put("totalCases", getCount(conn, "SELECT COUNT(*) FROM casetable"));
            jsonResponse.put("totalCriminals", getCount(conn, "SELECT COUNT(*) FROM criminal"));
            jsonResponse.put("totalOfficers", getCount(conn, "SELECT COUNT(*) FROM police"));
            jsonResponse.put("totalJudges", getCount(conn, "SELECT COUNT(*) FROM judge"));

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("error", "Database error occurred");
        }

        response.getWriter().write(jsonResponse.toString());
    }

    private int getCount(Connection conn, String query) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
