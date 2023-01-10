package cz.czechitas.pexeso.service;

public interface RoundService {
    void selectCard(int cardId, String boardHash);

    Integer getRoundCountByBoardHash(String boardHash);
}
