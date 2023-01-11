package cz.czechitas.pexeso.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import cz.czechitas.pexeso.dao.CardDao;
import cz.czechitas.pexeso.dao.ImageDao;
import cz.czechitas.pexeso.dao.RoundDao;
import cz.czechitas.pexeso.exception.CardNotFoundException;
import cz.czechitas.pexeso.model.Board;
import cz.czechitas.pexeso.model.Card;
import cz.czechitas.pexeso.model.Image;
import cz.czechitas.pexeso.model.Round;

@Service
public class RoundServiceImpl implements RoundService {

    private final RoundDao roundDao;
    private final CardDao cardDao;
    private final ImageDao imageDao;
    private final BoardService boardService;

    public RoundServiceImpl(RoundDao roundDao, CardDao cardDao, ImageDao imageDao, BoardService boardService) {
        this.roundDao = roundDao;
        this.cardDao = cardDao;
        this.imageDao = imageDao;
        this.boardService = boardService;
    }

    @Override
    public void selectCard(int cardId, String boardHash) {
        Board board = boardService.getBoardByHash(boardHash);
        Card selectedCard = findCardInBoard(board, cardId);
        Optional<Round> lastRoundOptional = roundDao.getLastRoundByBoardId(board.getId());

        if (isLastRoundFull(lastRoundOptional)) {
            handleFullRound(lastRoundOptional.get(), board);
        }

        saveCardToBoard(lastRoundOptional, selectedCard, board);
    }

    @Override
    public Integer getRoundCountByBoardHash(String boardHash) {
        Board board = boardService.getBoardByHash(boardHash);
        return roundDao.getRoundCountByBoard(board);
    }

    private void saveCardToBoard(Optional<Round> roundOptional, Card card, Board board) {
        if (roundOptional.isEmpty() || isLastRoundFull(roundOptional)) {
            roundDao.saveFirstCard(card.getId(), board);
        } else {
            roundDao.saveSecondCard(card.getId(), board);
        }
        cardDao.setIsCardSelected(true, board.getId(), card.getId());
    }

    private Card findCardInBoard(Board board, int cardId) {
        return board.getCards().stream()
                .filter(boardCard -> boardCard.getId() == cardId)
                .findFirst()
                .orElseThrow(() -> new CardNotFoundException(
                        "Cannot find card with id " + cardId + " in board id" + board.getId()));
    }

    private void handleFullRound(Round round, Board board) {
        if (isRoundCorrect(round)) {
            pairsRoundCards(round);
        }
        unselectRoundCards(round);
    }

    private boolean isLastRoundFull(Optional<Round> roundOptional) {
        if (roundOptional.isEmpty()) {
            return false;
        }
        Round round = roundOptional.get();
        return round.getFirstCardId().isPresent() && round.getSecondCardId().isPresent();
    }

    private boolean isRoundCorrect(Round round) {
        Image firstCard = imageDao.getImageByCardId(round.getFirstCardId().get());
        Image secondCard = imageDao.getImageByCardId(round.getSecondCardId().get());
        return firstCard.getId() == secondCard.getId();
    }

    private void unselectRoundCards(Round round) {
        cardDao.setIsCardSelected(false, round.getBoardId(), round.getFirstCardId().get());
        cardDao.setIsCardSelected(false, round.getBoardId(), round.getSecondCardId().get());
    }

    private void pairsRoundCards(Round round) {
        cardDao.setIsCardPaired(true, round.getBoardId(), round.getFirstCardId().get());
        cardDao.setIsCardPaired(true, round.getBoardId(), round.getSecondCardId().get());
    }
}
