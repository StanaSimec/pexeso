package cz.czechitas.pexeso.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cz.czechitas.pexeso.model.Board;

public class BoardRowMapper implements RowMapper<Board> {

    @Override
    public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
        Board board = new Board();
        board.setId(rs.getInt("id"));
        board.setHash(rs.getString("hash"));
        return board;
    }
}
