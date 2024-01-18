package com.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import com.example.Users;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    //check session attribute "user".
    //for GET request if the session attribute "user" does not exist, redirect to the /login.jsp page, else redirect to the /user/hello.jsp.
    //for POST request check the request parameters. "login" should exist in Users and the request parameter "password" shouldn't be empty.
    // If parameters are correct set session attribute "user" and redirect to /user/hello.jsp, else forward to the /login.jsp.
    Users users = com.example.Users.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String redirectTo = "/login.jsp";

        try{
            HttpSession session = request.getSession(true);

            if(session.getAttribute("user") != null) {
                redirectTo = "/user/hello.jsp";
            }
        }finally {
            response.sendRedirect(redirectTo);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        boolean userExists = false;
        List<String> registeredUsers = users.getUsers();
        if (login != null){
            userExists = registeredUsers.stream().anyMatch(u -> u.equals(login));
        }

        if(userExists && password != null && !password.isEmpty()){
                request.getSession(true).setAttribute("user", login);
                response.sendRedirect("/user/hello.jsp");
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
