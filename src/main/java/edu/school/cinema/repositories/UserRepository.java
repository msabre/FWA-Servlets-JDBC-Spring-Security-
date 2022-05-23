package edu.school.cinema.repositories;

import edu.school.cinema.models.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Repository("userRepository")
public class UserRepository implements ApplicationContextAware {

    private JdbcTemplate jdbcTemplate;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
    }

    public User save(User user) {
        String query = "INSERT INTO fwa.USER(NAME, LAST_NAME, MIDDLE_NAME, PHONE_NUMBER, PASSWORD, EMAIL) VALUES(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                query, 
                new Object[] {user.getFirstName(), user.getLastName(), user.getMiddleName(), user.getPhoneNumber(), user.getPassword(), user.getEmail()},
                new int[] {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR}
        );
        return user;
    }

    public String findPasswordByEmail(String email) {
        String query = "SELECT usr.PASSWORD FROM fwa.USER usr WHERE usr.EMAIL = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{email}, new int[] {Types.VARCHAR}, String.class);
    }

    public User findByEmail(String email) {
        String query = "SELECT * FROM fwa.USER usr WHERE usr.EMAIL = ?";
        return jdbcTemplate.query(query, new ResultSetCustomExtractor(), email);
    }

    public static class ResultSetCustomExtractor implements ResultSetExtractor<User> {
        @Override
        public User extractData(ResultSet rs) throws SQLException, DataAccessException {
            User user = new User();
            if (rs.next()) {
                user.setId(rs.getLong("ID"));
                user.setFirstName(rs.getString("NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                user.setMiddleName(rs.getString("MIDDLE_NAME"));
                user.setPhoneNumber(rs.getString("PHONE_NUMBER"));
                user.setEmail(rs.getString("EMAIL"));
            }
            return user;
        }
    }
}
