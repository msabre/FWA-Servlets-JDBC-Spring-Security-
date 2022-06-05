package edu.school.cinema.services;

import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;

public class MyCustomLoginFailureHandler extends ForwardAuthenticationFailureHandler {

    public MyCustomLoginFailureHandler(String forwardUrl) {
        super(forwardUrl);
    }
}