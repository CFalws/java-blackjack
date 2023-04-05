package dto;

import domain.player.Participant;

import java.util.ArrayList;
import java.util.List;

public class ParticipantResultsDto {

    private final List<ResultDto> participantsResultDto;

    public ParticipantResultsDto(final List<ResultDto> participantsResultDto) {
        this.participantsResultDto = participantsResultDto;
    }

    public static ParticipantResultsDto of(final List<Participant> participants, final List<Integer> participantEarnings) {
        validateSizeEquality(participants, participantEarnings);

        final List<ResultDto> participantResultDtoList = new ArrayList<>();

        for (int i = 0; i < participants.size(); i++) {
            final Participant participant = participants.get(i);
            final Integer earning = participantEarnings.get(i);
            participantResultDtoList.add(new ParticipantResultDto(participant, earning));
        }

        return new ParticipantResultsDto(participantResultDtoList);
    }

    private static void validateSizeEquality(final List<Participant> players, final List<Integer> playerEarnings) {
        if (players.size() != playerEarnings.size()) {
            throw new IllegalArgumentException("플레이어와 상금의 개수가 일치하지 않습니다");
        }
    }

    public List<ResultDto> getResults() {
        return participantsResultDto;
    }
}
