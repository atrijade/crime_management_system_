package com.crime.management;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


@WebServlet("/AddCrimeServlet")
public class AddCrimeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String crimeID = request.getParameter("crimeID");
        String type = request.getParameter("type");
        String time = request.getParameter("time");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String streetName = request.getParameter("streetName");
        String criminalID = request.getParameter("criminalID");
        String judgeID = request.getParameter("judgeID");
        String officerID = request.getParameter("officerID");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms_project", "root", "@Anavatti123");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO crime VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, crimeID);
            ps.setString(2, type);
            ps.setString(3, time);
            ps.setString(4, address);
            ps.setString(5, city);
            ps.setString(6, streetName);
            ps.setString(7, criminalID);
            ps.setString(8, judgeID);
            ps.setString(9, officerID);

            ps.executeUpdate();
            response.sendRedirect("add-crime.html");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
