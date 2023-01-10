package cz.czechitas.pexeso.dao;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cz.czechitas.pexeso.exception.DatabaseException;
import cz.czechitas.pexeso.model.Board;
import cz.czechitas.pexeso.model.Round;
import cz.czechitas.pexeso.rowmapper.RoundRowMapper;

@Repository
public class RoundDaoImpl implements RoundDao {

    private final JdbcTemplate jdbcTemplate;

    public RoundDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Round> getLastRoundByBoardId(int boardId) {
        String sql = "SELECT * FROM round WHERE boardId = ? ORDER BY stamp DESC LIMIT 1;";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, new RoundRowMapper(), boardId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (Exception exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public void saveFirstCard(int firstCardId, Board board) {
        String sql = "INSERT INTO round (boardId, firstCardId) VALUES (?,?);";
        try {
            jdbcTemplate.update(sql, board.getId(), firstCardId);
        } catch (Exception exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public void saveSecondCard(int secondCardId, Board board) {
        String sql = "UPDATE round SET secondCardId = ? WHERE boardId = ?;";
        try {
            jdbcTemplate.update(sql, secondCardId, board.getId());
        } catch (Exception exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public Integer getRoundCountByBoard(Board board) {
        String sql = "SELECT COUNT(*) FROM round WHERE boardId = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, board.getId());
        } catch (Exception exception) {
            throw new DatabaseException(exception);
        }
    }
}
