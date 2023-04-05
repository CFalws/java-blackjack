package dto;

import domain.player.Participant;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

public class ParticipantsCardsDto {

    private final List<Participant> participants;

    public ParticipantsCardsDto(final List<Participant> participants) {
        this.participants = participants;
    }

    public List<CardsDto> getParticipantsCards() {
        return participants.stream()
                           .map(ParticipantCardsDto::new)
                           .collect(toUnmodifiableList());
    }
}
