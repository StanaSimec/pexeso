package cz.czechitas.pexeso.service;

import java.lang.StackWalker.Option;
import java.util.Optional;

import cz.czechitas.pexeso.model.Board;

public interface BoardService {
    Board createBoard();

    Optional<Board> getBoardById(int boardId);
}
