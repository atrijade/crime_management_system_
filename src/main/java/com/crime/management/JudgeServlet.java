package com.crime.management;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/JudgeServlet")
public class JudgeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("addOrUpdateCase".equals(action)) {
            String caseID = request.getParameter("caseID");
            String status = request.getParameter("status");
            String verdict = request.getParameter("verdict");

            try (Connection conn = DatabaseConnection.initializeDatabase()) {
                String sql = "INSERT INTO casetable (caseID, status, verdict) VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE status=?, verdict=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, caseID);
                stmt.setString(2, status);
                stmt.setString(3, verdict);
                stmt.setString(4, status);
                stmt.setString(5, verdict);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    response.sendRedirect("judge.html?success=Case updated successfully");
                } else {
                    response.sendRedirect("judge.html?error=Failed to update case");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("judge.html?error=Database error");
            }
        }
    }
}
