package cz.czechitas.pexeso.model;

public class Card {
    private int id;
    private String imagePath;
    private boolean isSelected;
    private boolean isPaired;

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setPaired(boolean isPaired) {
        this.isPaired = isPaired;
    }

    public boolean getIsPaired() {
        return isPaired;
    }

    public boolean isShowed() {
        return isSelected || isPaired;
    }
}
