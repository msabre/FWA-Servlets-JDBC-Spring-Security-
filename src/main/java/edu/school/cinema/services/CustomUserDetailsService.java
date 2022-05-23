package edu.school.cinema.services;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService, ApplicationContextAware {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String password = userService.findPasswordByEmail(email);
        if (password == null) {
            throw new UsernameNotFoundException("Not fount user by email");
        }
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(email)
                .password(password)
                .roles("USER")
                .build();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.userService = applicationContext.getBean("userService", UserService.class);
    }
}
