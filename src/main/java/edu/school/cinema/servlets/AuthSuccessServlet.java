package edu.school.cinema.servlets;

import edu.school.cinema.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authSuccess")
public class AuthSuccessServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = (String) req.getSession().getAttribute("username");
        User user = userService.findByEmail(email);
        req.getSession().setAttribute("user", user);
        req.getRequestDispatcher("/main/profile").forward(req, resp);
    }
}
