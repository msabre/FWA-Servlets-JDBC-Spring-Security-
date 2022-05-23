package edu.school.cinema.servlets;

import edu.school.cinema.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/signUp")
public class SignUpServlet extends BaseServlet {

    private static final String SUCCESS_MESSAGE = "Thank you for registration";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        User user = new User();
        user.setLastName(req.getParameter("lastName"));
        user.setFirstName(req.getParameter("firstName"));
        user.setMiddleName(req.getParameter("middleName"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        user.setPhoneNumber(req.getParameter("phoneNumber"));

        Set<ConstraintViolation<User>> errors = userService.isValid(user);
        if (!errors.isEmpty()) {
            String answer = errors.stream().map(err -> err.getPropertyPath() + ": " + err.getMessage()).collect(Collectors.joining(System.lineSeparator()));
            putBody(answer, resp.getOutputStream());
            return;
        }

        PasswordEncoder bCryptPasswordEncoder = applicationContext.getBean("passwordEncoder", PasswordEncoder.class);
        String encryptPass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptPass);

        userService.saveUser(user);
        putBody(SUCCESS_MESSAGE, resp.getOutputStream());
    }
}
