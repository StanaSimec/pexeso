package cz.czechitas.pexeso.dao;

import java.util.Optional;

import cz.czechitas.pexeso.model.Board;

public interface BoardDao {
    int createBoard();

    Optional<Board> getBoardById(int boardId);
}
