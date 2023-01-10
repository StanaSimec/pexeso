package cz.czechitas.pexeso.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cz.czechitas.pexeso.exception.DatabaseException;
import cz.czechitas.pexeso.model.Card;
import cz.czechitas.pexeso.rowmapper.CardRowMapper;

@Service
public class CardDaoImpl implements CardDao {

    private final JdbcTemplate jdbcTemplate;

    public CardDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Card> getCardsForBoardId(int boardId) {
        String sql = "SELECT c.id, i.imagePath, c.selected, c.paired " +
                "FROM card c " +
                "JOIN image i ON c.imageId = i.id " +
                "WHERE c.boardId = ? " +
                "ORDER BY c.id;";
        try {
            return jdbcTemplate.query(sql, new CardRowMapper(), boardId);
        } catch (Exception exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public void setIsCardSelected(boolean isSelected, int boardId, int cardId) {
        String sql = "UPDATE card c SET c.selected = ? WHERE c.id = ?;";
        try {
            jdbcTemplate.update(sql, isSelected, cardId);
        } catch (Exception exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public void setIsCardPaired(boolean isPaired, int boardId, int cardId) {
        String sql = "UPDATE card c SET c.paired = ? WHERE c.id = ?;";
        try {
            jdbcTemplate.update(sql, isPaired, cardId);
        } catch (Exception exception) {
            throw new DatabaseException(exception);
        }
    }
}
