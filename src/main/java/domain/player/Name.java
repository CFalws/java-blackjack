package domain.player;

import java.util.Objects;

public class Name {

    private static final int NAME_LENGTH_UPPER_BOUND = 5;

    private final String name;

    public Name(final String name) {
        validateNameLength(name);
        this.name = name;
    }

    private void validateNameLength(final String name) {
        if (name.length() > NAME_LENGTH_UPPER_BOUND) {
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
