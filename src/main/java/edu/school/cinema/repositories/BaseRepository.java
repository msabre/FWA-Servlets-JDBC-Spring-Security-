package edu.school.cinema.repositories;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseRepository implements ApplicationContextAware {

    protected JdbcTemplate jdbcTemplate;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
    }
}
