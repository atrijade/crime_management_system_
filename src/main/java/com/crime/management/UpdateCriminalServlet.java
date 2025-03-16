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


@WebServlet("/UpdateCriminalServlet")
public class UpdateCriminalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String criminalID = request.getParameter("criminalID");
        String name = request.getParameter("name");
        String nationalID = request.getParameter("nationalID");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String streetName = request.getParameter("streetName");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms_project", "root", "@Anavatti123");
            PreparedStatement ps = conn.prepareStatement("UPDATE criminal SET name=?, nationalID=?, address=?, city=?, streetName=? WHERE criminalID=?");
            ps.setString(1, name);
            ps.setString(2, nationalID);
            ps.setString(3, address);
            ps.setString(4, city);
            ps.setString(5, streetName);
            ps.setString(6, criminalID);

            ps.executeUpdate();
            response.sendRedirect("update-criminal.html");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}

