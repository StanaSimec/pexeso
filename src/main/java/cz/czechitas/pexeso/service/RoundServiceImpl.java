package cz.czechitas.pexeso.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import cz.czechitas.pexeso.dao.BoardDao;
import cz.czechitas.pexeso.dao.CardDao;
import cz.czechitas.pexeso.dao.ImageDao;
import cz.czechitas.pexeso.dao.RoundDao;
import cz.czechitas.pexeso.model.Board;
import cz.czechitas.pexeso.model.Card;
import cz.czechitas.pexeso.model.Image;
import cz.czechitas.pexeso.model.Round;

@Service
public class RoundServiceImpl implements RoundService {

    private final RoundDao roundDao;
    private final CardDao cardDao;
    private final ImageDao imageDao;
    private final BoardDao boardDao;

    public RoundServiceImpl(RoundDao roundDao, CardDao cardDao, ImageDao imageDao, BoardDao boardDao) {
        this.roundDao = roundDao;
        this.cardDao = cardDao;
        this.imageDao = imageDao;
        this.boardDao = boardDao;
    }

    @Override
    public void selectCard(Card card, Board board) {

        Optional<Round> lastRoundOptional = getLastRoundForBoardId(board);
        if (lastRoundOptional.isPresent()) {
            Round lastRound = lastRoundOptional.get();
            if (isRoundFull(lastRound)) {
                handleFullRound(lastRound, board);
            }
        }

        cardDao.setIsCardSelected(true, board.getId(), card.getId());

        if (isRoundEmpty(lastRoundOptional)) {
            roundDao.saveFirstCard(card.getId(), board);
        } else {
            roundDao.saveSecondCard(card.getId(), board);
        }
    }

    @Override
    public Integer getRoundCountByBoardHash(String boardHash) {
        Optional<Board> boardOptional = boardDao.getBoardByHash(boardHash);
        if(boardOptional.isPresent()) {
            return roundDao.getRoundCountByBoard(boardOptional.get());
        }
        return 0;
    }

    private void handleFullRound(Round round, Board board) {
        if (isRoundCorrect(round)) {
            pairsRoundCards(board, round);
        }
        unselectRoundCards(board, round);
    }

    private Optional<Round> getLastRoundForBoardId(Board board) {
        return roundDao.getLastRoundByBoardId(board.getId());
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
