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

        Card selectedCard = board.getCards().stream()
                .filter(boardCard -> boardCard.getId() == cardId)
                .findFirst()
                .orElseThrow(() -> new CardNotFoundException(
                        "Cannot find card with id " + cardId + " in board id" + board.getId()));

        Optional<Round> lastRoundOptional = roundDao.getLastRoundByBoardId(board.getId());
        if (lastRoundOptional.isPresent()) {
            Round lastRound = lastRoundOptional.get();
            if (isRoundFull(lastRound)) {
                handleFullRound(lastRound, board);
            }
        }

        cardDao.setIsCardSelected(true, board.getId(), selectedCard.getId());

        if (isRoundEmpty(lastRoundOptional)) {
            roundDao.saveFirstCard(selectedCard.getId(), board);
        } else {
            roundDao.saveSecondCard(selectedCard.getId(), board);
        }
    }

    @Override
    public Integer getRoundCountByBoardHash(String boardHash) {
        Board board = boardService.getBoardByHash(boardHash);
        return roundDao.getRoundCountByBoard(board);
    }

    private void handleFullRound(Round round, Board board) {
        if (isRoundCorrect(round)) {
            pairsRoundCards(board, round);
        }
        unselectRoundCards(board, round);
    }

    private boolean isRoundFull(Round round) {
        return round.getFirstCardId().isPresent() && round.getSecondCardId().isPresent();
    }

    private boolean isRoundCorrect(Round round) {
        if (isRoundFull(round)) {
            Image firstCard = imageDao.getImageByCardId(round.getFirstCardId().get());
            Image secondCard = imageDao.getImageByCardId(round.getSecondCardId().get());
            return firstCard.getId() == secondCard.getId();
        }
        return false;
    }

    private void unselectRoundCards(Board board, Round round) {
        cardDao.setIsCardSelected(false, board.getId(), round.getFirstCardId().get());
        cardDao.setIsCardSelected(false, board.getId(), round.getSecondCardId().get());
    }

    private void pairsRoundCards(Board board, Round round) {
        cardDao.setIsCardPaired(true, board.getId(), round.getFirstCardId().get());
        cardDao.setIsCardPaired(true, board.getId(), round.getSecondCardId().get());
    }

    private boolean isRoundEmpty(Optional<Round> lastRoundOptional) {
        return !lastRoundOptional.isPresent() || isRoundFull(lastRoundOptional.get());
    }
}
