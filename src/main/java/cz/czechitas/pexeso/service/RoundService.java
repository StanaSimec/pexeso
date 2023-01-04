package cz.czechitas.pexeso.service;

import cz.czechitas.pexeso.model.Board;
import cz.czechitas.pexeso.model.Card;

public interface RoundService {
    void selectCard(Card card, Board board);
}
