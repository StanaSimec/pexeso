package cz.czechitas.pexeso.service;

import java.util.Optional;

import cz.czechitas.pexeso.model.Board;

public interface BoardService {
    Board createBoard();

    Optional<Board> getBoardByHash(String boardHash);
}
