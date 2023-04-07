package domain.player;

import domain.card.Card;
import domain.card.Deck;
import domain.card.Hand;
import domain.game.BettingAmount;
import domain.game.Score;
import domain.state.playerstate.PlayerReady;
import domain.state.playerstate.PlayerState;

import java.util.List;

public class Player implements Participant {

    private final Name name;
    private PlayerState playerState;

    public Player(final Name name, final PlayerState playerState) {
        this.name = name;
        this.playerState = playerState;
    }

    public Player(final Name name, final BettingAmount bettingAmount) {
        this(name, new PlayerReady(bettingAmount));
    }

    @Override
    public void receiveInitialCards(final Deck deck) {
        playerState = playerState.receiveCards(deck);
    }

    @Override
    public boolean isDrawable() {
        return playerState.isDrawable();
    }

    @Override
    public void receiveCards(final Deck deck) {
        this.playerState = playerState.receiveCards(deck);
    }

    @Override
    public void stand() {
        playerState = playerState.stand();
    }

    @Override
    public Score calculateScore() {
        return playerState.calculateScore();
    }

    @Override
    public int getEarning(final Score otherScore) {
        return playerState.getEarning(otherScore);
    }

    @Override
    public String getName() {
        return name.getName();
    }

    @Override
    public List<Card> getCards() {
        return playerState.getCards();
    }

    @Override
    public Hand getHand() {
        return playerState.getHand();
    }
}
