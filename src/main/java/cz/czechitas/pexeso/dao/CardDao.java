package cz.czechitas.pexeso.dao;

import java.util.List;

import cz.czechitas.pexeso.model.Card;

public interface CardDao {
    List<Card> getCardsForBoardId(int boardId);

    void setIsCardSelected(boolean isSelected, int boardId, int cardId);

    void setIsCardPaired(boolean isPaired, int boardId, int cardId);
}
