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
    Users users = com.example.Users.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String redirectTo = "/login.jsp";

        try{
            HttpSession session = request.getSession(false);
            if(session == null){
                throw new ServletException("Login: Session doesn't exist");
            }else{
                if(session.getAttribute("user") != null){
                    redirectTo = "/user/hello.jsp";
                }
            }
        }finally {
            response.sendRedirect(redirectTo);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        List<String> registeredUsers = users.getUsers();
        boolean userExists = registeredUsers.stream().anyMatch(u -> u.equals(login));

        if((login != null && password != null) && (userExists && password.isEmpty())){
                request.getSession(true).setAttribute("user", login);
                response.sendRedirect("/user/hello.jsp");
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
