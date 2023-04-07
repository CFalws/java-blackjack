package domain.player;

import java.util.Objects;

public class Name {

    private static final int NAME_LENGTH_UPPER_BOUND = 5;
    private static final int NAME_LENGTH_LOWER_BOUND = 1;

    private final String name;

    private Name(final String name) {
        validateNameLengthInRange(name);
        validateNameNotEqualToDealer(name);
        this.name = name;
    }

    public static Name from(final String name) {
        return new Name(name.strip());
    }

    private void validateNameNotEqualToDealer(final String name) {
        final String dealerName = Dealer.DEALER_NAME;

        if (Objects.equals(dealerName, name)) {
            throw new IllegalArgumentException("이름을 " + dealerName + "으로 할 수 없습니다");
        }
    }

    private void validateNameLengthInRange(final String name) {
        if (name.length() > NAME_LENGTH_UPPER_BOUND || name.length() < NAME_LENGTH_LOWER_BOUND) {
            throw new IllegalArgumentException("이름은 1자 이상 5자 이하입니다");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Name other = (Name) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
