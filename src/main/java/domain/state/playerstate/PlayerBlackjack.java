package domain.state.playerstate;

import domain.card.Card;
import domain.card.Deck;
import domain.card.Hand;
import domain.game.BettingAmount;
import domain.game.Score;

import java.util.List;

public class PlayerBlackjack implements PlayerState {

    private final Hand hand;
    private final BettingAmount bettingAmount;

    public PlayerBlackjack(final Hand hand, final BettingAmount bettingAmount) {
        this.hand = hand;
        this.bettingAmount = bettingAmount;
    }

    @Override
    public boolean isDrawable() {
        return false;
    }

    @Override
    public PlayerState receiveCards(final Deck deck) {
        throw new UnsupportedOperationException("블랙잭 상태에선 카드를 받을 수 없습니다");
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
        if (dealerScore.isEqualTo(Score.BLACKJACK_SCORE)) {
            return bettingAmount.getDrawEarning();
        }
        return bettingAmount.getBlackjackWinEarning();
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
