package roomescape.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entities.Theme;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ThemeRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Theme> findAllThemes(){
        String sql = "SELECT * FROM THEME";
        List<Theme> themes = jdbcTemplate.query(sql, new RowMapper<Theme>() {
            @Override
            public Theme mapRow(ResultSet rs, int rowNum) throws SQLException {
                Theme theme = new Theme(
                  rs.getLong("id"),
                  rs.getString("name"),
                  rs.getString("description"),
                  rs.getString("thumbnail")
                );
                return theme;
            }
        });

        return themes;
    }

    public Theme save(Theme theme){
        String sql = "INSERT INTO THEME (name, description, thumbnail) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
              sql,
              new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDescription());
            ps.setString(3, theme.getThumbnail());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Theme(id, theme.getName(), theme.getDescription(), theme.getThumbnail());
    }

    public void deleteById(Long id){
        String sql = "DELETE FROM THEME WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}