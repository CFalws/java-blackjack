package view;

import domain.player.Name;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public List<String> readPlayerNames() {
        System.out.println("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)");

        final String userInput = scanner.nextLine();

        return Arrays.asList(userInput.split(",", -1));
    }

    public String readHitOrStand(final String name) {
        System.out.println();
        System.out.println(name + "는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)");

        return scanner.nextLine();
    }

    public int readBettingAmount(final Name playerName) {
        System.out.println(playerName.getName() + "의 배팅 금액은?");

        final String bettingAmountString = scanner.nextLine();

        return toInteger(bettingAmountString);
    }

    private int toInteger(final String bettingAmountString) {
        try {
            return Integer.parseInt(bettingAmountString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자여야 합니다");
        }
    }
}
