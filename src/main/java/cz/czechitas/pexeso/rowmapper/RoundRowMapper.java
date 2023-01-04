package cz.czechitas.pexeso.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import cz.czechitas.pexeso.model.Round;

public class RoundRowMapper implements RowMapper<Round> {

    @Override
    public Round mapRow(ResultSet rs, int rowNum) throws SQLException {
        Round round = new Round();
        round.setId(rs.getInt("id"));
        round.setBoardId(rs.getInt("boardId"));
        round.setFirstCardId(getOptionalFromCardIdInt(rs.getInt("firstCardId")));
        round.setSecondCardId(getOptionalFromCardIdInt(rs.getInt("secondCardId")));
        return round;
    }

    private Optional<Integer> getOptionalFromCardIdInt(int cardId) {
        return cardId == 0 ? Optional.empty() : Optional.of(cardId);
    }
}
