package cz.czechitas.pexeso.dao;

import java.util.Optional;

import cz.czechitas.pexeso.model.Board;
import cz.czechitas.pexeso.model.Round;

public interface RoundDao {
    Optional<Round> getLastRoundByBoardId(int boardId);

    void saveFirstCard(int firstCardId, Board board);

    void saveSecondCard(int secondCardId, Board board);

    Integer getRoundCountByBoard(Board board);
}
