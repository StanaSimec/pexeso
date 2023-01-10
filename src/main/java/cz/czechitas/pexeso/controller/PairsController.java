package cz.czechitas.pexeso.controller;

import cz.czechitas.pexeso.model.Board;
import cz.czechitas.pexeso.service.BoardService;
import cz.czechitas.pexeso.service.RoundService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        Board board = boardService.getBoardByHash(boardHash);
        boolean isBoardFinished = board.getCards().stream()
                .allMatch(card -> card.getIsPaired() || card.getIsSelected());
        if (isBoardFinished) {
            return "redirect:/result/" + boardHash;
        }

        model.addAttribute("board", board);
        return "board";
    }

    @PostMapping("/pexeso/{boardHash}/{cardId}")
    public String turnCard(@PathVariable("boardHash") String boardHash, @PathVariable("cardId") Integer cardId) {
        roundService.selectCard(cardId, boardHash);
        return "redirect:/pexeso/" + boardHash;
    }
}
