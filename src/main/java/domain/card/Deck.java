package domain.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck {

    private final LinkedList<Card> cards;

    private Deck(final LinkedList<Card> cards) {
        this.cards = cards;
    }

    public static Deck createFullDeck() {
        final LinkedList<Card> cards = new LinkedList<>();

        addAllCards(cards);

        return new Deck(cards);
    }

    private static void addAllCards(final LinkedList<Card> cards) {
        for (final Symbol symbol : Symbol.values()) {
            addAllCardsOf(symbol, cards);
        }
    }

    private static void addAllCardsOf(final Symbol symbol, final LinkedList<Card> cards) {
        for (final Number number : Number.values()) {
            final Card card = Card.of(number, symbol);
            cards.add(card);
        }
    }

    public List<Card> drawCards(int count) {
        validateCountInRange(count);

        final List<Card> drawnCards = new ArrayList<>();

        while (count-- > 0) {
            drawnCards.add(drawCard());
        }

        return drawnCards;
    }

    private void validateCountInRange(final int count) {
        if (count < 1) {
            throw new IllegalArgumentException("1장 이상 뽑아야 합니다");
        }
        if (count > cards.size()) {
            throw new IllegalArgumentException("카드가 부족합니다");
        }
    }

    public Card drawCard() {
        return cards.pollLast();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}
