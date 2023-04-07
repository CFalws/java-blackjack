package domain.state;

import domain.card.Card;
import domain.card.Deck;
import domain.card.Hand;
import domain.game.Score;

import java.util.List;

public interface ParticipantState {

    boolean isDrawable();

    ParticipantState receiveCards(Deck deck);

    Score calculateScore();

    List<Card> getCards();

    Hand getHand();
}
