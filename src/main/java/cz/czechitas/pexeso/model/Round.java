package cz.czechitas.pexeso.model;

import java.util.Optional;

public class Round {
    private int id;
    private int boardId;
    private Optional<Integer> firstCardId;
    private Optional<Integer> secondCardId;
    private String stamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public Optional<Integer> getFirstCardId() {
        return firstCardId;
    }

    public void setFirstCardId(Optional<Integer> firstCardId) {
        this.firstCardId = firstCardId;
    }

    public Optional<Integer> getSecondCardId() {
        return secondCardId;
    }

    public void setSecondCardId(Optional<Integer> secondCardId) {
        this.secondCardId = secondCardId;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }
}
