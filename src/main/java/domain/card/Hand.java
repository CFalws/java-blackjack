package domain.card;

import domain.game.Score;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    public static final int INITIAL_DEALING_CARDS_COUNT = 2;

    private final List<Card> cards;

    public Hand(final List<Card> cards) {
        this.cards = cards;
    }

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void addCards(final List<Card> other) {
        cards.addAll(other);
    }

    public void addCard(final Card card) {
        cards.add(card);
    }

    public Score calculateScore() {
        return Score.calculateScore(cards);
    }

    public List<Card> getCards() {
        return cards;
    }
}
