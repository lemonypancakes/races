package me.lemonypancakes.races.util;

public final class Unchecked {
    private Unchecked() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }
}
