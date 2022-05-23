package edu.school.cinema.servlets;

import edu.school.cinema.services.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class BaseServlet extends HttpServlet {

    protected UserService userService;
    protected ApplicationContext applicationContext;

    @Override
    public void init(ServletConfig servletConfig) {
        ServletContext servletContext = servletConfig.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext) servletContext.getAttribute("springContext");
        this.applicationContext = applicationContext;
        this.userService = applicationContext.getBean("userService", UserService.class);
    }

    protected void putBody(String body, OutputStream outputStream) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            IOUtils.write(body, writer);
        }
    }
}
