package edu.school.cinema.repositories;

import edu.school.cinema.models.Image;
import edu.school.cinema.models.Visit;
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
import java.util.ArrayList;
import java.util.List;

@Repository("visitRepository")
public class VisitRepository extends BaseRepository {

    public Visit save(Visit visit) {
        String query = "INSERT INTO fwa.VISIT(USER_ID, DATE, TIME, IP) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(
                query,
                new Object[] {visit.getUserId(), visit.getDate(), visit.getTime(), visit.getIp()},
                new int[] {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR}
        );
        return visit;
    }

    public List<Visit> findByUserId(long userId) {
        String query = "SELECT * FROM fwa.VISIT vis WHERE vis.USER_ID = ?";
        return jdbcTemplate.query(query, new ResultSetCustomExtractor(), userId);
    }

    public static class ResultSetCustomExtractor implements ResultSetExtractor<List<Visit>> {
        @Override
        public List<Visit> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Visit> visits = new ArrayList<>();
            while (rs.next()) {
                Visit vis = new Visit();
                vis.setUserId(rs.getInt("USER_ID"));
                vis.setDate(rs.getString("DATE"));
                vis.setTime(rs.getString("TIME"));
                vis.setIp(rs.getString("IP"));
                visits.add(vis);
            }
            return visits;
        }
    }
}
