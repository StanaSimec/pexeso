package cz.czechitas.pexeso.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cz.czechitas.pexeso.model.Card;

public class CardRowMapper implements RowMapper<Card> {

    @Override
    public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
        Card card = new Card();
        card.setId(rs.getInt("id"));
        card.setImagePath(rs.getString("imagePath"));
        card.setSelected(rs.getBoolean("selected"));
        card.setPaired(rs.getBoolean("paired"));
        return card;
    }

}
