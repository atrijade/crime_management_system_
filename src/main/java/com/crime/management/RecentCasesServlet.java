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
import java.sql.ResultSet;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/RecentCasesServlet")
public class RecentCasesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONArray recentCases = new JSONArray();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms_project", "root", "@Anavatti123");

            String query = "SELECT c.caseID, c.status, c.verdict, "
                    + "p.name AS officerName, p.station, "
                    + "j.name AS judgeName, j.court "
                    + "FROM casetable c "
                    + "LEFT JOIN police p ON c.officerID = p.officerID "
                    + "RIGHT JOIN judge j ON c.criminalID = j.judgeID "
                    + "ORDER BY c.caseID DESC LIMIT 5"; // Fetch latest 5 cases

            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                JSONObject caseObj = new JSONObject();
                caseObj.put("caseID", rs.getInt("caseID"));
                caseObj.put("status", rs.getString("status"));
                caseObj.put("verdict", rs.getString("verdict"));
                caseObj.put("officerName", rs.getString("officerName"));
                caseObj.put("station", rs.getString("station"));
                caseObj.put("judgeName", rs.getString("judgeName"));
                caseObj.put("court", rs.getString("court"));

                recentCases.put(caseObj);
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.getWriter().write(recentCases.toString());
    }
}
