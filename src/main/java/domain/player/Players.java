package domain.player;

import domain.card.Deck;
import domain.game.BettingAmount;
import domain.game.Score;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

public class Players {

    private final List<Participant> players;

    public Players(final List<Participant> players) {
        this.players = players;
    }

    public static Players of(final List<Name> playerNames, final List<BettingAmount> bettingAmounts) {
        validateSizeEquality(playerNames, bettingAmounts);

        final List<Participant> participants = new ArrayList<>();

        for (int i = 0; i < playerNames.size(); i++) {
            final Name name = playerNames.get(i);
            final BettingAmount bettingAmount = bettingAmounts.get(i);
            participants.add(new Player(name, bettingAmount));
        }

        return new Players(participants);
    }

    private static void validateSizeEquality(final List<Name> playerNames, final List<BettingAmount> bettingAmounts) {
        if (playerNames.size() != bettingAmounts.size()) {
            throw new IllegalArgumentException("player 이름과 베팅 금액이 대응되지 않습니다");
        }
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
