package cz.czechitas.pexeso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.ui.Model;
import cz.czechitas.pexeso.service.RoundService;

@Controller
public class ResultController {
    private RoundService roundService;

    public ResultController(RoundService roundService) {
        this.roundService = roundService;
    }

    @GetMapping("/result/{boardHash}")
    public String boardResult(@PathVariable String boardHash, Model model) {
        Integer roundCount = roundService.getRoundCountByBoardHash(boardHash);
        model.addAttribute("roundCount", roundCount);
        return "summary";
    }
}
