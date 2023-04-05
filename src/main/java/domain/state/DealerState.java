package domain.state;

import domain.card.Deck;

public interface DealerState extends ParticipantState {

    @Override
    DealerState receiveCards(Deck deck);
}
