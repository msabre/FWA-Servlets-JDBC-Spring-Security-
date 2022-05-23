package edu.school.cinema.repositories;

import edu.school.cinema.models.Image;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("visitRepository")
public class VisitRepository implements ApplicationContextAware {

    private JdbcTemplate jdbcTemplate;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
    }

    public List<Image> findByUserId(long userId) {
        String query = "SELECT * FROM fwa.IMAGE img WHERE img.USER_ID = ?";
        return jdbcTemplate.query(query, new ResultSetCustomExtractor(), userId);
    }

    public static class ResultSetCustomExtractor implements ResultSetExtractor<List<Image>> {
        @Override
        public List<Image> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Image> images = new ArrayList<>();
            if (rs.next()) {
                Image img = new Image();
                img.setId(rs.getLong("ID"));
                img.setFileName(rs.getString("FILE_NAME"));
                img.setMime(rs.getString("MIME"));
                img.setPath(rs.getString("PATH"));
                img.setSize(rs.getInt("SIZE"));
                img.setUserId(rs.getInt("USER_ID"));
            }
            return images;
        }
    }
}
