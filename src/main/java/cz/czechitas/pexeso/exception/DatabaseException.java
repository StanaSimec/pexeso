package cz.czechitas.pexeso.exception;

public class DatabaseException extends RuntimeException {

    public DatabaseException(Exception e) {
        super(e);
    }
}
