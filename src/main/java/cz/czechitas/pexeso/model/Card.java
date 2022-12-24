package cz.czechitas.pexeso.model;
import java.awt.*;

public class Card {
    private final int id;
    private PexesoImage image;
    private boolean isTurned;
    private boolean isFound;

    public Card(int id, PexesoImage image) {
        this.id = id;
        this.image = image;
        this.isTurned = false;
        this.isFound = false;
    }

    public int getId() {
        return id;
    }

    public PexesoImage getImage() {
        return image;
    }

    public void setImage(PexesoImage image) {
        this.image = image;
    }

    public boolean isShowed() {
        return isFound || isTurned;
    }

    public void setTurned(boolean turned) {
        isTurned = turned;
    }

    public void setFound(boolean found) {
        isFound = found;
    }
}
