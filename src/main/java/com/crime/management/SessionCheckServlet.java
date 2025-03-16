package com.crime.management;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.IOException;

@WebServlet("/SessionCheckServlet")
public class SessionCheckServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        HttpSession session = request.getSession(false);
        JSONObject jsonResponse = new JSONObject();

        if (session != null && session.getAttribute("user") != null) {
            jsonResponse.put("loggedIn", true);
            jsonResponse.put("user", session.getAttribute("user"));
        } else {
            jsonResponse.put("loggedIn", false);
        }

        response.getWriter().write(jsonResponse.toString());
    }
}
