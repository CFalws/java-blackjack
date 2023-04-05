package dto;

import domain.card.Card;

public class CardDto {

    private final Card card;

    public CardDto(final Card card) {
        this.card = card;
    }

    public String getSymbol() {
        return card.getSymbol();
    }

    public String getNumber() {
        return card.getNumber();
    }
}
