package domain.state;

import domain.card.Hand;
import domain.game.BettingAmount;
import domain.game.Score;

import static domain.game.Score.BLACKJACK_SCORE;
import static domain.game.Score.DEALER_STAND_LOWER_BOUND;

public class StatusCalculator {

    public DealerState determineDealerStatus(final Hand hand) {
        final Score score = hand.calculateScore();

        if (score.isLessThan(DEALER_STAND_LOWER_BOUND)) {
            return new DealerHit(hand);
        }
        return new DealerStand(hand);
    }

    public PlayerState determinePlayerStatus(final Hand hand, final BettingAmount bettingAmount) {
        final Score score = hand.calculateScore();

        if (score.isLessThan(BLACKJACK_SCORE)) {
            return new PlayerHit(hand, bettingAmount);
        }
        return getBustOrBlackjack(hand, bettingAmount);
    }

    private PlayerState getBustOrBlackjack(final Hand hand, final BettingAmount bettingAmount) {
        final Score score = hand.calculateScore();

        if (score.isEqualTo(BLACKJACK_SCORE)) {
            return new PlayerBlackjack(hand, bettingAmount);
        }
        return new PlayerBust(hand, bettingAmount);
    }
}
