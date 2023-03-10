package cz.czechitas.pexeso.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(DatabaseException.class)
    public String handledatabaseException(Model model) {
        model.addAttribute("message", "Chyba v databázi, opakujte akci později");
        return "error";
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public String handleBoardNotFoundException(Model model) {
        model.addAttribute("message", "Hra nebyla nalezena, vytvořte novou hru.");
        return "error";
    }

    @ExceptionHandler(CardNotFoundException.class)
    public String handleCardNotFoundException(Model model) {
        model.addAttribute("message", "Karta nebyla nalezena, zvolte jinou kartu.");
        return "error";
    }
}
