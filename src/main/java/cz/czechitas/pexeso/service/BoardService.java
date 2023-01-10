package cz.czechitas.pexeso.service;

import cz.czechitas.pexeso.model.Board;

public interface BoardService {
    Board createBoard();

    Board getBoardByHash(String boardHash);
}
