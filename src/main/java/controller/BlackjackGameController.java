package controller;

import domain.game.BettingAmount;
import domain.game.BlackjackGame;
import domain.player.Name;
import domain.player.Participant;
import dto.*;
import view.InputView;
import view.OutputView;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BlackjackGameController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Map<PlayerCommand, Function<BlackjackGame, Participant>> commandToAction;

    public BlackjackGameController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.commandToAction = new EnumMap<>(PlayerCommand.class);
    }

    public void run() {
        setActions();

        final BlackjackGame blackjackGame = createBlackjackGame();

        dealInitialHand(blackjackGame);

        dealCardToPlayers(blackjackGame);
        dealCardToDealer(blackjackGame);

        showGameResult(blackjackGame);
    }

    private void setActions() {
        commandToAction.put(PlayerCommand.HIT, this::hitCurrentPlayer);
        commandToAction.put(PlayerCommand.STAND, this::standCurrentPlayer);
    }

    private BlackjackGame createBlackjackGame() {
        final List<Name> playerNames = inputView.readPlayerNames();
        final List<BettingAmount> bettingAmounts = inputView.readBettingAmounts(playerNames);

        return new BlackjackGame(playerNames, bettingAmounts);
    }

    private void dealInitialHand(final BlackjackGame blackjackGame) {
        blackjackGame.dealInitialHand();

        outputView.printInitialDealingInfo();

        final Participant dealer = blackjackGame.getDealer();
        final List<Participant> players = blackjackGame.getPlayers();

        outputView.printDealtCards(new InitialDealerCardsDto(dealer));
        outputView.printDealtCards(new ParticipantsCardsDto(players));
    }

    private void dealCardToPlayers(final BlackjackGame blackjackGame) {
        while (blackjackGame.hasPlayerToDeal()) {
            final Participant playerToDeal = blackjackGame.getPlayerToDeal();
            final String hitOrStandString = inputView.readHitOrStand(playerToDeal.getName());
            final PlayerCommand hitOrStand = PlayerCommand.from(hitOrStandString);

            final Participant currentPlayer = commandToAction.get(hitOrStand).apply(blackjackGame);

            outputView.printDealtCards(new ParticipantCardsDto(currentPlayer));
        }
    }

    private Participant hitCurrentPlayer(final BlackjackGame blackjackGame) {
        return blackjackGame.hitCurrentPlayer();
    }

    private Participant standCurrentPlayer(final BlackjackGame blackjackGame) {
        return blackjackGame.standCurrentPlayer();
    }

    private void dealCardToDealer(final BlackjackGame blackjackGame) {
        blackjackGame.dealCardsToDealer();

        final Participant dealer = blackjackGame.getDealer();

        outputView.printDealerDealtInfo(new ParticipantCardsDto(dealer));
    }

    private void showGameResult(final BlackjackGame blackjackGame) {
        final Participant dealer = blackjackGame.getDealer();
        final List<Participant> players = blackjackGame.getPlayers();

        final int dealerEarning = blackjackGame.getDealerEarning();
        final List<Integer> playerEarnings = blackjackGame.getPlayerEarnings();

        final ResultDto dealerResultDto = new ParticipantResultDto(dealer, dealerEarning);
        final ParticipantResultsDto playerResultsDto = ParticipantResultsDto.of(players, playerEarnings);

        printResult(dealerResultDto, playerResultsDto);
    }

    private void printResult(final ResultDto dealerResultDto, final ParticipantResultsDto playerResultsDto) {
        outputView.printParticipantCardsWithScore(dealerResultDto);
        outputView.printPlayersCardsWithScore(playerResultsDto);

        outputView.printWinningAmountShowInfo();

        outputView.printParticipantEarning(dealerResultDto);
        outputView.printPlayerEarnings(playerResultsDto);
    }
}
