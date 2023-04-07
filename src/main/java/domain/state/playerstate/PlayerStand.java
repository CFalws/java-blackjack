package domain.state.playerstate;

import domain.card.Card;
import domain.card.Deck;
import domain.card.Hand;
import domain.game.BettingAmount;
import domain.game.Score;

import java.util.List;

public class PlayerStand implements PlayerState {

    private final Hand hand;
    private final BettingAmount bettingAmount;

    public PlayerStand(final Hand hand, final BettingAmount bettingAmount) {
        this.hand = hand;
        this.bettingAmount = bettingAmount;
    }

    @Override
    public boolean isDrawable() {
        return false;
    }

    @Override
    public PlayerState receiveCards(final Deck deck) {
        throw new UnsupportedOperationException("스탠드 상태에선 카드를 받을 수 없습니다");
    }

    @Override
    public Score calculateScore() {
        return hand.calculateScore();
    }

    @Override
    public PlayerState stand() {
        throw new UnsupportedOperationException("스탠드 할 수 없는 상태입니다");
    }

    @Override
    public int getEarning(final Score dealerScore) {
        final Score playerScore = hand.calculateScore();

        if (isPlayerWin(dealerScore, playerScore)) {
            return bettingAmount.getWinEarning();
        }
        if (isDraw(dealerScore, playerScore)) {
            return bettingAmount.getDrawEarning();
        }
        return bettingAmount.getLoseEarning();
    }

    private boolean isDraw(final Score dealerScore, final Score playerScore) {
        return dealerScore.isEqualTo(playerScore.getScore());
    }

    private boolean isPlayerWin(final Score dealerScore, final Score playerScore) {
        return dealerScore.isLargerThan(Score.BLACKJACK_SCORE) || dealerScore.isLessThan(playerScore.getScore());
    }

    @Override
    public List<Card> getCards() {
        return List.copyOf(hand.getCards());
    }

    @Override
    public Hand getHand() {
        return hand;
    }
}
