package domain.state.playerstate;

import domain.card.Deck;
import domain.game.Score;
import domain.state.ParticipantState;

public interface PlayerState extends ParticipantState {

    @Override
    PlayerState receiveCards(Deck deck);

    PlayerState stand();

    int getEarning(Score dealerScore);
}
