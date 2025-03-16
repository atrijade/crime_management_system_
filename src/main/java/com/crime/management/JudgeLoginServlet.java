package com.crime.management;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/JudgeLoginServlet")
public class JudgeLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String judgeID = request.getParameter("judgeID");
        String password = request.getParameter("password");

        try (Connection conn = DatabaseConnection.initializeDatabase()) {
            String sql = "SELECT * FROM judge WHERE judgeID=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, judgeID);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("judgeID", judgeID);
                response.sendRedirect("judge.html");
            } else {
                response.sendRedirect("judge-login.html?error=Invalid credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("judge-login.html?error=Database error");
        }
    }
}

