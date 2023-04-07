package util;

import java.util.function.Function;
import java.util.function.Supplier;

public final class Repeater {

    private Repeater() {
    }

    public static <T> T repeatUntilNoIAE(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static <T, R> R repeatUntilNoIAE(Function<T, R> function, T argument) {
        while (true) {
            try {
                return function.apply(argument);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
