package cz.czechitas.pexeso.dao;

import java.util.List;

import cz.czechitas.pexeso.model.Image;

public interface ImageDao {
    List<Image> getRandomImages();

    void saveImagesToBoardId(List<Image> images, int boardId);

    Image getImageByCardId(int cardId);
}
