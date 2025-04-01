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

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms_project", "root", "@Anavatti123");

            PreparedStatement pst = con.prepareStatement("SELECT * FROM admin WHERE username=? AND password=?");
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("admin", username);

                // Redirect to admin.html with sessionStorage update
                response.setContentType("text/html");
                response.getWriter().println("<script>");
                response.getWriter().println("sessionStorage.setItem('admin', '" + username + "');");
                response.getWriter().println("window.location.href = 'admin.html';");
                response.getWriter().println("</script>");
            } else {
                response.setContentType("text/html");
                response.getWriter().println("<script>");
                response.getWriter().println("alert('Invalid Credentials');");
                response.getWriter().println("window.location.href = 'AdminLogin.html';");
                response.getWriter().println("</script>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
