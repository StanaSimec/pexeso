package cz.czechitas.pexeso.dao;

import cz.czechitas.pexeso.model.Board;

public interface BoardDao {
    int createBoard();

    Board getBoardByHash(String hash);

    void setHashToBoardId(String hash, int boardId);
}
