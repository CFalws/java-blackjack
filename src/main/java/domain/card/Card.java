package domain.card;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Card {

    private static final Map<Integer, Card> CACHE = new HashMap<>();

    private final Number number;
    private final Symbol symbol;

    private Card(final Number number, final Symbol symbol) {
        this.number = number;
        this.symbol = symbol;
    }

    public static Card of(final Number number, final Symbol symbol) {
        return CACHE.computeIfAbsent(toKey(number, symbol), ignored -> new Card(number, symbol));
    }

    private static Integer toKey(final Number number, final Symbol symbol) {
        return Objects.hash(number, symbol);
    }

    public boolean isAce() {
        return number == Number.ACE;
    }

    public int getScore() {
        return number.getScore();
    }

    public String getSymbol() {
        return symbol.getSymbol();
    }

    public String getNumber() {
        return number.getSymbol();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Card card = (Card) o;
        return number == card.number && symbol == card.symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, symbol);
    }

    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                ", symbol=" + symbol +
                '}';
    }
}
