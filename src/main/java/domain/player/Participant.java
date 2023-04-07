package domain.player;

import domain.card.Card;
import domain.card.Deck;
import domain.card.Hand;
import domain.game.Score;

import java.util.List;

public interface Participant {

    void receiveInitialCards(Deck deck);

    boolean isDrawable();

    void receiveCards(Deck deck);

    void stand();

    Score calculateScore();

    int getEarning(Score otherScore);

    String getName();

    List<Card> getCards();

    Hand getHand();
}
