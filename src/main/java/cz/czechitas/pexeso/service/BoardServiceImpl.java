package cz.czechitas.pexeso.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import cz.czechitas.pexeso.dao.BoardDao;
import cz.czechitas.pexeso.dao.CardDao;
import cz.czechitas.pexeso.dao.ImageDao;
import cz.czechitas.pexeso.model.Board;
import cz.czechitas.pexeso.model.Card;
import cz.czechitas.pexeso.model.Image;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardDao boardDao;
    private final ImageDao imageDao;
    private final CardDao cardDao;

    public BoardServiceImpl(BoardDao boardDao, ImageDao imageDao, CardDao cardDao) {
        this.boardDao = boardDao;
        this.imageDao = imageDao;
        this.cardDao = cardDao;
    }

    @Override
    public Board createBoard() {
        Board board = new Board();
        int boardId = boardDao.createBoard();
        board.setId(boardId);

        List<Image> boardImages = createBoardImages();
        imageDao.saveImagesToBoardId(boardImages, boardId);

        List<Card> cards = cardDao.getCardsForBoardId(boardId);
        board.setCards(cards);

        addHashToBoard(board);

        return board;
    }

    @Override
    public Board getBoardByHash(String boardHash) {
        Board board = boardDao.getBoardByHash(boardHash);
        List<Card> cards = cardDao.getCardsForBoardId(board.getId());
        board.setCards(cards);
        return board;
    }

    private List<Image> createBoardImages() {
        List<Image> firstOfPairImages = imageDao.getRandomImages();
        List<Image> secondOfPairImages = new ArrayList<>(firstOfPairImages);
        firstOfPairImages.addAll(secondOfPairImages);
        Collections.shuffle(firstOfPairImages);
        return firstOfPairImages;
    }

    private void addHashToBoard(Board board) {
        String hash = DigestUtils.sha256Hex(String.valueOf(board.getId()));
        boardDao.setHashToBoardId(hash, board.getId());
        board.setHash(hash);
    }
}
