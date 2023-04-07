package domain.state.dealerstate;

import domain.card.Deck;
import domain.state.ParticipantState;

public interface DealerState extends ParticipantState {

    @Override
    DealerState receiveCards(Deck deck);
}
