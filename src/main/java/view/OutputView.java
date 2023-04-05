package view;

import domain.card.Hand;
import dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class OutputView {

    private static final String CARD_DELIMITER = ",";

    public void printInitialDealingInfo() {
        System.out.println(System.lineSeparator() + "모든 참여자에게 카드를 두 장씩 나누었습니다" + System.lineSeparator());
    }

    public void printDealtCards(final ParticipantsCardsDto participantsCardsDto) {
        final List<CardsDto> cardsDtoList = participantsCardsDto.getParticipantsCards();

        cardsDtoList.forEach(this::printDealtCards);
    }

    public void printDealerDealtInfo(final CardsDto dealerCards) {
        final List<CardDto> cards = dealerCards.getCards();

        if (cards.size() > Hand.INITIAL_DEALING_CARDS_COUNT) {
            System.out.println(System.lineSeparator() + "딜러는 16 이하라 카드를 더 받았습니다." + System.lineSeparator());
        }
    }

    public void printPlayersCardsWithScore(final ParticipantResultsDto playerResultsDto) {
        playerResultsDto.getResults()
                .forEach(this::printParticipantCardsWithScore);
    }

    public void printParticipantCardsWithScore(final ResultDto dealerResultDto) {
        System.out.println(generateCardsWithScoreFormat(dealerResultDto));
    }

    public void printDealtCards(final CardsDto cardsDto) {
        System.out.println(generateCardsFormat(cardsDto));
    }

    private String generateCardsWithScoreFormat(final ResultDto dealerResultDto) {
        return generateCardsFormat(dealerResultDto) + " - " + generateScoreFormat(dealerResultDto.getScore());
    }

    private String generateCardsFormat(final CardsDto cardsDto) {
        return cardsDto.getParticipantName() + " 카드: " + cardsDto.getCards()
                .stream()
                .map(cardDto -> cardDto.getNumber() + cardDto.getSymbol())
                .collect(Collectors.joining(CARD_DELIMITER));
    }

    public void printWinningAmountShowInfo() {
        System.out.println(System.lineSeparator() + "## 최종 수익");
    }

    private String generateScoreFormat(final int score) {
        return "결과: " + score;
    }

    public void printParticipantEarning(final ResultDto resultDto) {
        System.out.println(resultDto.getParticipantName() + ": " + resultDto.getEarning());
    }

    public void printPlayerEarnings(final ParticipantResultsDto playerResultsDto) {
        final List<ResultDto> results = playerResultsDto.getResults();
        results.forEach(this::printParticipantEarning);
    }
}
