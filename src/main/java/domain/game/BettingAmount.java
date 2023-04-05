package domain.game;

public class BettingAmount {

    private static final int MINIMUM_UNIT = 1000;

    private final int amount;

    public BettingAmount(final int amount) {
        validateBettingAmount(amount);
        this.amount = amount;
    }

    public static BettingAmount from(final String amountString) {
        validateNumberFormat(amountString);
        return new BettingAmount(Integer.parseInt(amountString));
    }

    private static void validateNumberFormat(final String amountString) {
        try {
            Integer.parseInt(amountString);
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException("숫자로 입력해주세요");
        }
    }

    private void validateBettingAmount(final int amount) {
        validatePositive(amount);
        validateMultipleOfThousand(amount);
    }

    private void validatePositive(final int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("베팅 금액은 0보다 커야 합니다");
        }
    }

    private void validateMultipleOfThousand(final int amount) {
        if (amount % MINIMUM_UNIT != 0) {
            throw new IllegalArgumentException("1000 단위로 돈을 걸어주세요");
        }
    }

    public int getBlackjackWinEarning() {
        return (int) (1.5 * amount);
    }

    public int getWinEarning() {
        return amount;
    }

    public int getDrawEarning() {
        return 0;
    }

    public int getLoseEarning() {
        return -1 * amount;
    }
}
