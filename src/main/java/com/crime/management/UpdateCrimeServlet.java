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


@WebServlet("/UpdateCrimeServlet")
public class UpdateCrimeServlet extends HttpServlet {
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
            PreparedStatement ps = conn.prepareStatement("UPDATE crime SET type=?, time=?, address=?, city=?, streetName=?, criminalID=?, judgeID=?, officerID=? WHERE crimeID=?");
            ps.setString(1, type);
            ps.setString(2, time);
            ps.setString(3, address);
            ps.setString(4, city);
            ps.setString(5, streetName);
            ps.setString(6, criminalID);
            ps.setString(7, judgeID);
            ps.setString(8, officerID);
            ps.setString(9, crimeID);

            ps.executeUpdate();
            response.sendRedirect("update-crime.html");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}

