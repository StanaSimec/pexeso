package cz.czechitas.pexeso;

import cz.czechitas.pexeso.model.Board;
import cz.czechitas.pexeso.model.Card;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class PairsController {
    private final Board board = BoardFactory.createBoard();
    private final PairChoice pairChoice = new PairChoice();

    @GetMapping("/board")
    public String board(Model model) {
        model.addAttribute("board", board);
        return "board";
    }

    @PostMapping("/board/{id}")
    public String turnCard(@PathVariable Integer id) {
        String redirectToBoardPath = "redirect:/board";

        if (pairChoice.isFull()) {
            if (pairChoice.isCorrect()) {
                pairChoice.updateChoicesToFound();
            }
            pairChoice.unselectChoices();
            pairChoice.cleanChoices();
        }

        Optional<Card> card = board.getCards().stream().filter(card1 -> card1.getId() == id).findFirst();

        if (card.isEmpty()) {
            return redirectToBoardPath;
        }

        Card currentCard = card.get();
        currentCard.setTurned(true);
        pairChoice.addCard(currentCard);
        return redirectToBoardPath;
    }
}
