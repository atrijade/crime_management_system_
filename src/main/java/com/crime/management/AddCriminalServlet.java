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


@WebServlet("/AddCriminalServlet")
public class AddCriminalServlet extends HttpServlet {
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
            PreparedStatement ps = conn.prepareStatement("INSERT INTO criminal VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, criminalID);
            ps.setString(2, name);
            ps.setString(3, nationalID);
            ps.setString(4, address);
            ps.setString(5, city);
            ps.setString(6, streetName);

            ps.executeUpdate();
            response.sendRedirect("add-criminal.html");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
