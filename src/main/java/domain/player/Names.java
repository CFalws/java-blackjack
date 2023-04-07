package domain.player;

import java.util.HashSet;
import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toUnmodifiableList;

public class Names {

    private final List<Name> names;

    public Names(final List<Name> names) {
        validateNoDuplicateNames(names);
        this.names = names;
    }

    public static Names from(final List<String> nameStrings) {
        return nameStrings.stream()
                          .map(Name::from)
                          .collect(collectingAndThen(toUnmodifiableList(), Names::new));
    }

    private void validateNoDuplicateNames(final List<Name> names) {
        if (new HashSet<>(names).size() != names.size()) {
            throw new IllegalArgumentException("이름에 중복이 있습니다");
        }
    }

    public List<Name> getNames() {
        return names;
    }
}
