package cz.czechitas.pexeso;

import cz.czechitas.pexeso.model.Card;
import cz.czechitas.pexeso.model.PexesoImage;

import java.util.ArrayList;
import java.util.List;

public class PairChoice {
    private List<Card> choices;

    public PairChoice() {
        this.choices = new ArrayList<>();
    }

    public boolean isCorrect(){
        if(isFull()) {
            PexesoImage compareImage = choices.get(0).getImage();
            return choices.stream().allMatch(image -> image.getImage() == compareImage);
        }
        return false;
    }

    public void addCard(Card card) {
        choices.add(card);
    }

    public boolean isFull() {
        int cardPerRoundCount = 2;
        return choices.size() >= cardPerRoundCount;
    }

    public void cleanChoices() {
        this.choices = new ArrayList<>();
    }

    public void updateChoicesToFound() {
        choices.forEach(choice -> choice.setFound(true));
    }

    public void unselectChoices() {
        choices.forEach(choice -> choice.setTurned(false));
    }
}
