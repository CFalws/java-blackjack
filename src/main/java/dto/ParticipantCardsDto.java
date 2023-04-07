package dto;

import domain.card.Card;
import domain.player.Participant;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

public class ParticipantCardsDto implements CardsDto {

    private final Participant participant;

    public ParticipantCardsDto(final Participant participant) {
        this.participant = participant;
    }

    @Override
    public String getParticipantName() {
        return participant.getName();
    }

    @Override
    public List<CardDto> getCards() {
        final List<Card> cards = participant.getCards();

        return cards.stream()
                    .map(CardDto::new)
                    .collect(toUnmodifiableList());
    }
}
