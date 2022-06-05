package edu.school.cinema.filters;

import edu.school.cinema.models.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {

    List<String> permitUrls = Arrays.asList("/main/images", "/main/profile", "/main/authSuccess");

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (!permitUrls.contains(req.getRequestURI())) {
            User user = (User) req.getSession().getAttribute("user");
            if (user != null) {
                res.sendRedirect("/main/profile");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
