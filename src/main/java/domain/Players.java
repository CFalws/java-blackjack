package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class Players {// TODO: 2023/03/07 Player를 copy 해서 내보내고 싶다.
    private final List<Player> players;

    private Players(List<Player> players) {
        validateNotEmpty(players);
        this.players = players;
    }

    public static Players from(List<String> names) {
        return names.stream()
                    .map(Player::new)
                    .collect(collectingAndThen(toUnmodifiableList(), Players::new));
    }

    private void validateNotEmpty(List<Player> players) {
        if (players.isEmpty()) {
            throw new IllegalArgumentException("플레이어는 1명 이상이어야 합니다.");
        }
    }

    public void receiveCard(Deck deck) {
        players.forEach(player -> player.receiveCard(deck.draw()));
    }

    public Player getPlayerToDecide() {
        Player player = players.stream()
                               .filter(Player::isDrawable)
                               .findFirst()
                               .orElseThrow(() -> new IllegalStateException("카드를 받을 수 있는 플레이어가 없습니다."));
        return player;
    }

    public boolean hasAnyPlayerToDeal() {
        return players.stream()
                      .anyMatch(Player::isDrawable);
    }

    public void dealToCurrentPlayer(Card card) {
        getPlayerToDecide().receiveCard(card);
    }

    public void standCurrentPlayer() {
        getPlayerToDecide().stand();
    }

    public Map<String, PlayerOutcome> computeWinLoss(Hand dealerHand) {
        return players.stream()
                      .collect(toUnmodifiableMap(Player::name
                              , player -> player.computeWinLoss(dealerHand)));
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public List<String> getPlayerNames() {
        return players.stream()
                      .map(Player::name)
                      .collect(toUnmodifiableList());
    }
}
