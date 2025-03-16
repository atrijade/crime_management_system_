package com.crime.management;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/GetCasesServlet")
public class GetCasesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONArray casesArray = new JSONArray();

        String caseID = request.getParameter("caseID");
        String status = request.getParameter("status");

        try {
            Connection conn = DatabaseConnection.initializeDatabase();

            // Corrected SQL Query
            String query = "SELECT c.caseID, c.status, c.verdict, c.criminalID, " +
                    "p.name AS officerName FROM casetable c " +
                    "LEFT JOIN police p ON c.officerID = p.officerID WHERE 1=1";

            if (caseID != null && !caseID.isEmpty()) {
                query += " AND c.caseID = ?";
            }
            if (status != null && !status.isEmpty()) {
                query += " AND c.status = ?";
            }

            PreparedStatement stmt = conn.prepareStatement(query);
            int paramIndex = 1;
            if (caseID != null && !caseID.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(caseID));
            }
            if (status != null && !status.isEmpty()) {
                stmt.setString(paramIndex++, status);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JSONObject caseObj = new JSONObject();
                caseObj.put("caseID", rs.getInt("caseID"));
                caseObj.put("status", rs.getString("status"));
                caseObj.put("verdict", rs.getString("verdict"));
                caseObj.put("criminalID", rs.getInt("criminalID"));
                caseObj.put("officerName", rs.getString("officerName") != null ? rs.getString("officerName") : "Not Assigned");

                casesArray.put(caseObj);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.print(casesArray.toString());
        out.flush();
    }
}
