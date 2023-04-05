package domain.state;

import domain.card.Card;
import domain.card.Deck;
import domain.card.Hand;
import domain.game.Score;

import java.util.List;

public class DealerStand implements DealerState {

    private final Hand hand;

    public DealerStand(final Hand hand) {
        this.hand = hand;
    }

    @Override
    public boolean isDrawable() {
        return false;
    }

    @Override
    public DealerState receiveCards(final Deck deck) {
        throw new UnsupportedOperationException("스탠드 상태에선 카드를 받을 수 없습니다");
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
