package com.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/user/*")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException{}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpRequest.getSession(false);

        if (httpSession != null && httpSession.getAttribute("user") != null){
            chain.doFilter(request,response);
        } else {
            httpResponse.sendRedirect("/login.jsp");
        }
    }

    @Override
    public void destroy(){}
}