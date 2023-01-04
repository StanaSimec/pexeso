package cz.czechitas.pexeso.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

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
        } catch (DataAccessException | NullPointerException e) {
            return 0;
        }
    }

    @Override
    public Optional<Board> getBoardById(int boardId) {
        try {
        String sql = "SELECT * FROM board WHERE id = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, new BoardRowMapper(), boardId));
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
