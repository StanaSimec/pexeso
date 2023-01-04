package cz.czechitas.pexeso;

import cz.czechitas.pexeso.model.Board;
import cz.czechitas.pexeso.model.Card;
import cz.czechitas.pexeso.service.BoardService;
import cz.czechitas.pexeso.service.RoundService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class PairsController {

    private BoardService boardService;
    private RoundService roundService;

    public PairsController(BoardService boardService, RoundService roundService) {
        this.boardService = boardService;
        this.roundService = roundService;
    }

    @GetMapping("/pexeso")
    public String newBoard(Model model) {
        Board board = boardService.createBoard();
        return "redirect:/pexeso/" + board.getId();
    }

    @GetMapping("/pexeso/{boardId}")
    public String getBoard(@PathVariable Integer boardId, Model model) {
        Optional<Board> boardOptional = boardService.getBoardById(boardId);
        if (boardOptional.isEmpty()) {
            return "redirect:/pexeso";
        }
        model.addAttribute("board", boardOptional.get());
        return "board";
    }

    @PostMapping("/pexeso/{boardId}/{cardId}")
    public String turnCard(@PathVariable("boardId") Integer boardId, @PathVariable("cardId") Integer cardId) {
        Optional<Board> boardOptional = boardService.getBoardById(boardId);
        if (boardOptional.isEmpty()) {
            return "redirect:/pexeso";
        }

        Board board = boardOptional.get();
        Optional<Card> selectedCard = board.getCards().stream()
                .filter(boardCard -> boardCard.getId() == cardId)
                .findFirst();

        if (selectedCard.isEmpty()) {
            return "redirect:/pexeso/" + boardId;
        }

        roundService.selectCard(selectedCard.get(), board);
        return "redirect:/pexeso/" + boardId;
    }
}
