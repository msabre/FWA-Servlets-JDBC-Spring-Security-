package edu.school.cinema.listeners;

import edu.school.cinema.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

//        FilterRegistration.Dynamic encodingFilter = sce.getServletContext().addFilter("myWebFilter", 
//                (DelegatingFilterProxy) configApplicationContext.getBean("myWebFilter"));
//
//        encodingFilter.setInitParameter("blah", "blah");
//        encodingFilter.addMappingForUrlPatterns(null, false, "/*");

        sce.getServletContext().setAttribute("springContext", configApplicationContext);
    }
}
