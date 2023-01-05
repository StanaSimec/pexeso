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
        return "redirect:/pexeso/" + board.getHash();
    }

    @GetMapping("/pexeso/{boardHash}")
    public String getBoard(@PathVariable String boardHash, Model model) {
        Optional<Board> boardOptional = boardService.getBoardByHash(boardHash);
        if (boardOptional.isEmpty()) {
            return "redirect:/pexeso";
        }
        model.addAttribute("board", boardOptional.get());
        return "board";
    }

    @PostMapping("/pexeso/{boardHash}/{cardId}")
    public String turnCard(@PathVariable("boardHash") String boardHash, @PathVariable("cardId") Integer cardId) {
        Optional<Board> boardOptional = boardService.getBoardByHash(boardHash);
        if (boardOptional.isEmpty()) {
            return "redirect:/pexeso";
        }

        Board board = boardOptional.get();
        Optional<Card> selectedCard = board.getCards().stream()
                .filter(boardCard -> boardCard.getId() == cardId)
                .findFirst();

        if (selectedCard.isEmpty()) {
            return "redirect:/pexeso/" + board.getHash();
        }

        roundService.selectCard(selectedCard.get(), board);
        return "redirect:/pexeso/" + board.getHash();
    }
}
