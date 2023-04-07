package domain.player;

import domain.card.Deck;
import domain.game.Score;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

public class Players {

    private final List<Player> players;

    public Players(final List<Player> players) {
        this.players = players;
    }

    public void receiveInitialCards(final Deck deck) {
        players.forEach(player -> player.receiveInitialCards(deck));
    }

    public boolean hasPlayerToDeal() {
        return players.stream()
                      .anyMatch(Participant::isDrawable);
    }

    public Participant getPlayerToDeal() {
        return players.stream()
                      .filter(Participant::isDrawable)
                      .findFirst()
                      .orElseThrow(() -> new IllegalStateException("카드를 받을 수 있는 플레이어가 없습니다"));
    }

    public Participant hitCurrentPlayer(final Deck deck) {
        final Participant playerToHit = getPlayerToDeal();

        playerToHit.receiveCards(deck);

        return playerToHit;
    }

    public Participant standCurrentPlayer() {
        final Participant playerToStand = getPlayerToDeal();

        playerToStand.stand();

        return playerToStand;
    }

    public List<Integer> getPlayerEarnings(final Score dealerScore) {
        return players.stream()
                      .map(player -> player.getEarning(dealerScore))
                      .collect(toUnmodifiableList());
    }

    public List<Participant> getPlayers() {
        return List.copyOf(players);
    }
}
