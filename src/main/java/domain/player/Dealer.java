package domain.player;

import domain.card.Card;
import domain.card.Deck;
import domain.card.Hand;
import domain.game.Score;
import domain.state.DealerReady;
import domain.state.DealerState;

import java.util.List;

public class Dealer implements Participant {

    private static final String DEALER_NAME = "딜러";

    private DealerState dealerState;

    public Dealer() {
        this.dealerState = new DealerReady();
    }

    @Override
    public void receiveInitialCards(final Deck deck) {
        dealerState = dealerState.receiveCards(deck);
    }

    @Override
    public boolean isDrawable() {
        return dealerState.isDrawable();
    }

    @Override
    public void receiveCards(final Deck deck) {
        while (isDrawable()) {
            dealerState = dealerState.receiveCards(deck);
        }
    }

    @Override
    public void stand() {
        throw new UnsupportedOperationException("딜러는 stand 할 수 없습니다");
    }

    @Override
    public Score calculateScore() {
        return dealerState.calculateScore();
    }

    @Override
    public int getEarning(final Score otherScore) {
        throw new UnsupportedOperationException("딜러는 점수로 earning을 구하지 않습니다");
    }

    @Override
    public String getName() {
        return DEALER_NAME;
    }

    @Override
    public List<Card> getCards() {
        final List<Card> cards = dealerState.getCards();

        return List.copyOf(cards);
    }

    @Override
    public Hand getHand() {
        return dealerState.getHand();
    }
}
