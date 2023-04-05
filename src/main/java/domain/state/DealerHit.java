package domain.state;

import domain.card.Card;
import domain.card.Deck;
import domain.card.Hand;
import domain.game.Score;

import java.util.List;

public class DealerHit implements DealerState {

    private final Hand hand;

    public DealerHit(final Hand hand) {
        this.hand = hand;
    }

    @Override
    public boolean isDrawable() {
        return true;
    }

    @Override
    public DealerState receiveCards(final Deck deck) {
        final Card card = deck.drawCard();

        hand.addCard(card);

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
