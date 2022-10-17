package nextstep.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ThemeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ThemeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("theme")
                .usingGeneratedKeyColumns("id");
    }

    public Theme save(Theme theme) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(theme);
        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Theme(id, theme);
    }

    public List<Theme> findAll() {
        String sql = "select * from theme";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Theme(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("desc"),
                        resultSet.getInt("price")
                )
        );
    }

    public void deleteById(Long id) {
        String sql = "delete from theme where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsByName(String name) {
        return countByName(name) > 0;
    }

    private int countByName(String name) {
        String sql = "select count(*) from theme where name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, name);
    }
}