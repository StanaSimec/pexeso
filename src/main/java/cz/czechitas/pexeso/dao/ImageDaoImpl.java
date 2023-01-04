package cz.czechitas.pexeso.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cz.czechitas.pexeso.model.Image;
import cz.czechitas.pexeso.rowmapper.ImageRowMapper;

@Repository
public class ImageDaoImpl implements ImageDao {

    private final JdbcTemplate jdbcTemplate;

    public ImageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Image> getRandomImages() {
        String sql = "SELECT i.id, i.imagePath, FALSE as selected, FALSE as paired " +
                "FROM image i " +
                "ORDER BY RAND() LIMIT 6;";
        return jdbcTemplate.query(sql, new ImageRowMapper());
    }

    @Override
    public void saveImagesToBoardId(List<Image> images, int boardId) {
        String sql = "INSERT INTO card (boardId, imageId) VALUES (?,?);";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Image image = images.get(i);
                ps.setInt(1, boardId);
                ps.setInt(2, image.getId());
            }

            @Override
            public int getBatchSize() {
                return images.size();
            }
        });
    }

    @Override
    public Image getImageByCardId(int cardId) {
        String sql = "SELECT i.id, i.imagePath, c.selected, c.paired " +
                "FROM card c " +
                "JOIN image i ON i.id = c.imageId " +
                "WHERE c.id = ?;";
        return jdbcTemplate.queryForObject(sql, new ImageRowMapper(), cardId);
    }
}
