package cz.czechitas.pexeso;

import cz.czechitas.pexeso.model.Board;
import cz.czechitas.pexeso.model.Card;
import cz.czechitas.pexeso.model.PexesoImage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardFactory {
    public static Board createBoard() {
        List<Card> cards =
                Arrays.asList(
                        new Card(1, PexesoImage.ONE),
                        new Card(2, PexesoImage.TWO),
                        new Card(3, PexesoImage.THREE),
                        new Card(4, PexesoImage.FOUR),
                        new Card(5, PexesoImage.FIVE),
                        new Card(6, PexesoImage.SIX),
                        new Card(7, PexesoImage.ONE),
                        new Card(8, PexesoImage.TWO),
                        new Card(9, PexesoImage.THREE),
                        new Card(10, PexesoImage.FOUR),
                        new Card(11, PexesoImage.FIVE),
                        new Card(12, PexesoImage.SIX)
                );
        Collections.shuffle(cards);
        return new Board(cards);
    }
}
