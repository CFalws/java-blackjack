package dto;

import domain.game.Score;
import domain.player.Participant;

import java.util.List;

public class ParticipantResultDto implements ResultDto {

    private final Participant participant;
    private final int earning;

    public ParticipantResultDto(final Participant participant, final int earning) {
        this.participant = participant;
        this.earning = earning;
    }

    @Override
    public String getParticipantName() {
        return participant.getName();
    }

    @Override
    public List<CardDto> getCards() {
        return new ParticipantCardsDto(participant).getCards();
    }

    @Override
    public int getScore() {
        final Score score = participant.calculateScore();
        return score.getScore();
    }

    @Override
    public int getEarning() {
        return earning;
    }
}
