package view;

import domain.game.BettingAmount;
import domain.player.Name;

import java.util.*;
import java.util.stream.Collectors;

public class InputView {

    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public List<Name> readPlayerNames() {
        System.out.println("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)");

        final String rawNames = scanner.nextLine();

        final List<String> playerNames = Arrays.asList(rawNames.split(","));

        final List<String> trimmedPlayerNames = trim(playerNames);

        validatePlayerNames(trimmedPlayerNames);

        return toNames(trimmedPlayerNames);
    }

    private List<String> trim(final List<String> playerNames) {
        return playerNames.stream()
                          .map(String::trim)
                          .collect(Collectors.toUnmodifiableList());
    }

    private void validatePlayerNames(final List<String> playerNames) {
        validateHasNoBlankName(playerNames);
        validateNoDuplicateNames(playerNames);
    }

    private List<Name> toNames(final List<String> nameStrings) {
        return nameStrings.stream()
                          .map(Name::new)
                          .collect(Collectors.toUnmodifiableList());
    }

    private void validateHasNoBlankName(final List<String> playerNames) {
        playerNames.forEach(this::validatePlayerName);
    }

    private void validateNoDuplicateNames(final List<String> playerNames) {
        final Set<String> distinctNames = new HashSet<>(playerNames);

        if (distinctNames.size() != playerNames.size()) {
            throw new IllegalArgumentException("이름에 중복이 있습니다");
        }
    }

    private void validatePlayerName(final String playerName) {
        if (playerName.isBlank()) {
            throw new IllegalArgumentException("이름은 반드시 문자를 포함해야 합니다");
        }
    }

    public String readHitOrStand(final String name) {
        System.out.println(System.lineSeparator() + name + "는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)");
        return scanner.nextLine();
    }

    public List<BettingAmount> readBettingAmounts(final List<Name> playerNames) {
        final List<BettingAmount> bettingAmounts = new ArrayList<>();

        for (final Name playerName : playerNames) {
            readBettingAmount(bettingAmounts, playerName);
        }

        return bettingAmounts;
    }

    private void readBettingAmount(final List<BettingAmount> bettingAmounts, final Name playerName) {
        System.out.println(playerName.getName() + "의 배팅 금액은?");

        final String amountString = scanner.nextLine();

        bettingAmounts.add(BettingAmount.from(amountString));
    }
}
