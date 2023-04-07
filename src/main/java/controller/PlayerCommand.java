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
        if (isCodeNotExist(code)) {
            throw new IllegalArgumentException("카드를 더 받으려면 y, 그렇지 않은 경우 n을 입력하세요");
        }
    }

    private static boolean isCodeNotExist(final String code) {
        return Arrays.stream(values())
                     .noneMatch(playerCommand -> Objects.equals(code, playerCommand.code));
    }
}
