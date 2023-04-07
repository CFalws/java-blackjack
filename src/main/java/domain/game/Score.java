package domain.game;

import domain.card.Card;

import java.util.List;
import java.util.Objects;

public class Score {

    public static final int DEALER_STAND_LOWER_BOUND = 17;
    public static final int BLACKJACK_SCORE = 21;
    public static final int BUST_LOWER_BOUND = 22;
    private static final int ACE_BONUS_SCORE = 10;

    private final int score;

    public Score(final int score) {
        this.score = score;
    }


    public static Score calculateScore(final List<Card> cards) {
        int score = sumScores(cards);

        if (hasAce(cards)) {
            score = addBonusScoreIfNotBust(score);
        }

        return new Score(score);
    }

    private static int sumScores(final List<Card> cards) {
        return cards.stream()
                    .mapToInt(Card::getScore)
                    .sum();
    }

    private static boolean hasAce(final List<Card> cards) {
        return cards.stream()
                    .anyMatch(Card::isAce);
    }

    private static int addBonusScoreIfNotBust(final int score) {
        if (score + ACE_BONUS_SCORE >= BUST_LOWER_BOUND) {
            return score;
        }
        return score + ACE_BONUS_SCORE;
    }

    public boolean isLessThan(final int number) {
        return score < number;
    }

    public boolean isLargerThan(final int number) {
        return score > number;
    }

    public boolean isEqualTo(final int number) {
        return this.equals(new Score(number));
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Score otherScore = (Score) o;
        return score == otherScore.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }
}
