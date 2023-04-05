package domain.state;

import domain.card.Card;
import domain.card.Deck;
import domain.card.Hand;
import domain.game.BettingAmount;
import domain.game.Score;

import java.util.List;

import static domain.card.Hand.INITIAL_DEALING_CARDS_COUNT;
import static domain.game.Score.BLACKJACK_SCORE;


public class PlayerReady implements PlayerState {

    private final Hand hand;
    private final BettingAmount bettingAmount;

    public PlayerReady(final BettingAmount bettingAmount) {
        this.hand = new Hand();
        this.bettingAmount = bettingAmount;
    }

    @Override
    public boolean isDrawable() {
        return true;
    }

    @Override
    public PlayerState receiveCards(final Deck deck) {
        final List<Card> cards = deck.drawCards(INITIAL_DEALING_CARDS_COUNT);

        hand.addCards(cards);

        return nextStatus();
    }

    @Override
    public Score calculateScore() {
        return hand.calculateScore();
    }

    private PlayerState nextStatus() {
        final Score score = hand.calculateScore();

        if (score.isEqualTo(BLACKJACK_SCORE)) {
            return new PlayerBlackjack(hand, bettingAmount);
        }
        return new PlayerHit(hand, bettingAmount);
    }

    @Override
    public PlayerState stand() {
        throw new UnsupportedOperationException("스탠드 할 수 없는 상태입니다");
    }

    @Override
    public int getEarning(final Score dealerScore) {
        throw new UnsupportedOperationException("아직 게임이 진행 중입니다");
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
