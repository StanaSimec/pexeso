package cz.czechitas.pexeso.model;

import java.util.Collections;
import java.util.List;

public class Board {
    private List<Card> cards;

    public Board(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }
}
