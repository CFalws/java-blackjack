package controller;

import java.util.Arrays;
import java.util.Objects;

public enum PlayerCommand {

    HIT("y"),
    STAND("n"),
    ;

    private final String code;


    PlayerCommand(final String code) {
        this.code = code;
    }

    public static PlayerCommand from(final String code) {
        validateCodeExist(code);

        if (Objects.equals(code, HIT.code)) {
            return HIT;
        }
        return STAND;
    }

    private static void validateCodeExist(final String code) {
        final boolean isCodeNotExist = Arrays.stream(values())
                .noneMatch(playerCommand -> Objects.equals(code, playerCommand.code));

        if (isCodeNotExist) {
            throw new IllegalArgumentException("카드를 더 받으려면 y, 그렇지 않은 경우 n을 입력하세요");
        }
    }
}
