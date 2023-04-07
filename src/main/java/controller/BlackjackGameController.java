package controller;

import domain.game.BettingAmount;
import domain.game.BlackjackGame;
import domain.player.Name;
import domain.player.Names;
import domain.player.Participant;
import domain.player.Player;
import dto.*;
import view.InputView;
import view.OutputView;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static util.Repeater.repeatUntilNoIAE;

public class BlackjackGameController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Map<PlayerCommand, Function<BlackjackGame, Participant>> commandToAction;

    public BlackjackGameController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.commandToAction = new EnumMap<>(PlayerCommand.class);
        mapActions();
    }

    private void mapActions() {
        commandToAction.put(PlayerCommand.HIT, this::hitCurrentPlayer);
        commandToAction.put(PlayerCommand.STAND, this::standCurrentPlayer);
    }

    private Participant hitCurrentPlayer(final BlackjackGame blackjackGame) {
        return blackjackGame.hitCurrentPlayer();
    }

    private Participant standCurrentPlayer(final BlackjackGame blackjackGame) {
        return blackjackGame.standCurrentPlayer();
    }

    public void run() {
        final BlackjackGame blackjackGame = getNewBlackjackGame();

        dealInitialHand(blackjackGame);
        dealCardToPlayers(blackjackGame);
        dealCardToDealer(blackjackGame);

        showGameResult(blackjackGame);
    }

    private BlackjackGame getNewBlackjackGame() {
        final Names playerNames = repeatUntilNoIAE(this::readPlayerNames);
        final List<Player> players = createPlayersWithBet(playerNames);

        return new BlackjackGame(players);
    }

    private Names readPlayerNames() {
        return Names.from(inputView.readPlayerNames());
    }

    private List<Player> createPlayersWithBet(final Names playerNames) {
        return playerNames.getNames()
                          .stream()
                          .map(this::createPlayerWithBet)
                          .collect(toList());
    }

    private Player createPlayerWithBet(final Name name) {
        final BettingAmount bettingAmount = repeatUntilNoIAE(this::readBettingAmount, name);

        return new Player(name, bettingAmount);
    }

    private BettingAmount readBettingAmount(final Name name) {
        return new BettingAmount(inputView.readBettingAmount(name));
    }

    private void dealInitialHand(final BlackjackGame blackjackGame) {
        blackjackGame.dealInitialHand();

        outputView.printInitialDealingInfo();

        printInitialHandOfParticipants(blackjackGame);
    }

    private void printInitialHandOfParticipants(final BlackjackGame blackjackGame) {
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

            final Participant currentPlayer = commandToAction.get(hitOrStand)
                                                             .apply(blackjackGame);

            outputView.printDealtCards(new ParticipantCardsDto(currentPlayer));
        }
    }

    private void dealCardToDealer(final BlackjackGame blackjackGame) {
        blackjackGame.dealCardsToDealer();

        final Participant dealer = blackjackGame.getDealer();

        outputView.printDealerDealtInfo(new ParticipantCardsDto(dealer));
    }

    private void showGameResult(final BlackjackGame blackjackGame) {
        final ResultDto dealerResultDto = getDealerResult(blackjackGame);

        final ParticipantResultsDto playerResultsDto = getPlayersResult(blackjackGame);

        printResult(dealerResultDto, playerResultsDto);
    }

    private ResultDto getDealerResult(final BlackjackGame blackjackGame) {
        final Participant dealer = blackjackGame.getDealer();
        final int dealerEarning = blackjackGame.getDealerEarning();

        return new ParticipantResultDto(dealer, dealerEarning);
    }

    private ParticipantResultsDto getPlayersResult(final BlackjackGame blackjackGame) {
        final List<Participant> players = blackjackGame.getPlayers();
        final List<Integer> playerEarnings = blackjackGame.getPlayerEarnings();

        return ParticipantResultsDto.of(players, playerEarnings);
    }

    private void printResult(final ResultDto dealerResultDto, final ParticipantResultsDto playerResultsDto) {
        printCardsWithScore(dealerResultDto, playerResultsDto);

        printEarnings(dealerResultDto, playerResultsDto);
    }

    private void printCardsWithScore(final ResultDto dealerResultDto, final ParticipantResultsDto playerResultsDto) {
        outputView.printParticipantCardsWithScore(dealerResultDto);
        outputView.printPlayersCardsWithScore(playerResultsDto);
    }

    private void printEarnings(final ResultDto dealerResultDto, final ParticipantResultsDto playerResultsDto) {
        outputView.printWinningAmountShowInfo();

        outputView.printParticipantEarning(dealerResultDto);
        outputView.printPlayerEarnings(playerResultsDto);
    }
}
