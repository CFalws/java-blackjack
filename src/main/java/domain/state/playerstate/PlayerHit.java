package domain.state.playerstate;

import domain.card.Card;
import domain.card.Deck;
import domain.card.Hand;
import domain.game.BettingAmount;
import domain.game.Score;
import domain.state.StatusCalculator;

import java.util.List;

public class PlayerHit implements PlayerState {

    private final Hand hand;
    private final BettingAmount bettingAmount;

    public PlayerHit(final Hand hand, final BettingAmount bettingAmount) {
        this.hand = hand;
        this.bettingAmount = bettingAmount;
    }

    @Override
    public boolean isDrawable() {
        return true;
    }

    @Override
    public PlayerState receiveCards(final Deck deck) {
        final Card card = deck.drawCard();

        hand.addCard(card);

        return new StatusCalculator().determinePlayerStatus(hand, bettingAmount);
    }

    @Override
    public Score calculateScore() {
        return hand.calculateScore();
    }

    @Override
    public PlayerState stand() {
        return new PlayerStand(hand, bettingAmount);
    }

    @Override
    public int getEarning(final Score dealerScore) {
        throw new UnsupportedOperationException("아직 게임이 진행 중입니다");
    }

    @Override
    public List<Card> getCards() {
        return List.copyOf(hand.getCards());
    }

    @Override
    public Hand getHand() {
        return hand;
    }
}
