package domain.state;

import domain.card.Deck;
import domain.game.Score;

public interface PlayerState extends ParticipantState {

    @Override
    PlayerState receiveCards(Deck deck);

    PlayerState stand();

    int getEarning(Score dealerScore);
}
