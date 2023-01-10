package cz.czechitas.pexeso.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import cz.czechitas.pexeso.exception.BoardNotFoundException;
import cz.czechitas.pexeso.exception.DatabaseException;
import cz.czechitas.pexeso.model.Board;
import cz.czechitas.pexeso.rowmapper.BoardRowMapper;

@Repository
public class BoardDaoImpl implements BoardDao {

    private final JdbcTemplate jdbcTemplate;

    public BoardDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createBoard() {
        String sql = "INSERT INTO board () VALUES ();";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                return preparedStatement;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Board getBoardByHash(String boardHash) {
        try {
            String sql = "SELECT * FROM board WHERE hash = ?;";
            return jdbcTemplate.queryForObject(sql, new BoardRowMapper(), boardHash);
        } catch (EmptyResultDataAccessException e) {
            throw new BoardNotFoundException(e);
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void setHashToBoardId(String boardHash, int boardId) {
        try {
            String sql = "UPDATE board SET hash = ? WHERE id = ?;";
            jdbcTemplate.update(sql, boardHash, boardId);
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }
}
