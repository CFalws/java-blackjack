package dto;

import domain.card.Card;
import domain.player.Participant;

import java.util.List;

public class InitialDealerCardsDto implements CardsDto {

    private final Participant dealer;

    public InitialDealerCardsDto(final Participant dealer) {
        this.dealer = dealer;
    }

    @Override
    public String getParticipantName() {
        return dealer.getName();
    }

    @Override
    public List<CardDto> getCards() {
        final List<Card> hand = dealer.getCards();
        final Card firstCard = hand.get(0);

        return List.of(new CardDto(firstCard));
    }

}
