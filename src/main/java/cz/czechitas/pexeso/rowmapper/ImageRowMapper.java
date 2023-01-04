package cz.czechitas.pexeso.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cz.czechitas.pexeso.model.Image;

public class ImageRowMapper implements RowMapper<Image> {

    @Override
    public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
        Image card = new Image();
        card.setId(rs.getInt("id"));
        card.setImage(rs.getString("imagePath"));
        return card;
    }
}
