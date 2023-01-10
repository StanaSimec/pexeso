package cz.czechitas.pexeso.exception;

public class BoardNotFoundException extends RuntimeException {

    public BoardNotFoundException(Exception e) {
        super(e);
    }
}
