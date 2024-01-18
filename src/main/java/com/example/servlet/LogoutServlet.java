package com.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            String user = (String) session.getAttribute("user");

            if( user ==null || user.isEmpty()){
                throw new ServletException("User parameter is missing");
            }else {
                request.removeAttribute("user");
            }

            session.invalidate();

        }finally {
            response.sendRedirect("/login.jsp");
        }
    }
}
