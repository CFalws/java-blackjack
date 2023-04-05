package domain.state;

import domain.card.Card;
import domain.card.Deck;
import domain.card.Hand;
import domain.game.Score;

import java.util.List;

public class DealerReady implements DealerState {

    private final Hand hand;

    public DealerReady() {
        this.hand = new Hand();
    }

    @Override
    public boolean isDrawable() {
        return true;
    }

    @Override
    public DealerState receiveCards(final Deck deck) {
        final List<Card> cards = deck.drawCards(Hand.INITIAL_DEALING_CARDS_COUNT);

        hand.addCards(cards);

        return new StatusCalculator().determineDealerStatus(hand);
    }

    @Override
    public Score calculateScore() {
        return hand.calculateScore();
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
