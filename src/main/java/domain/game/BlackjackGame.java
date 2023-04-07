package domain.game;

import domain.card.Deck;
import domain.player.Dealer;
import domain.player.Participant;
import domain.player.Player;
import domain.player.Players;

import java.util.List;

public class BlackjackGame {

    private final Deck deck;
    private final Participant dealer;
    private final Players players;

    public BlackjackGame(final List<Player> players) {
        this.deck = Deck.createFullDeck();
        this.dealer = new Dealer();
        this.players = new Players(players);
    }

    public void dealInitialHand() {
        deck.shuffle();

        dealer.receiveInitialCards(deck);

        players.receiveInitialCards(deck);
    }

    public boolean hasPlayerToDeal() {
        return players.hasPlayerToDeal();
    }

    public Participant getPlayerToDeal() {
        return players.getPlayerToDeal();
    }

    public Participant hitCurrentPlayer() {
        return players.hitCurrentPlayer(deck);
    }

    public Participant standCurrentPlayer() {
        return players.standCurrentPlayer();
    }

    public void dealCardsToDealer() {
        dealer.receiveCards(deck);
    }

    public int getDealerEarning() {
        return getPlayerEarnings().stream()
                                  .mapToInt(earning -> -1 * earning)
                                  .sum();
    }

    public List<Integer> getPlayerEarnings() {
        return players.getPlayerEarnings(dealer.calculateScore());
    }

    public Participant getDealer() {
        return dealer;
    }

    public List<Participant> getPlayers() {
        return players.getPlayers();
    }
}
