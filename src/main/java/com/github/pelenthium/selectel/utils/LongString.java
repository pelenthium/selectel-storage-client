package com.github.pelenthium.selectel.utils;

public class LongString {

    private long value;

    public LongString(String value) {
        try {
            this.value = Long.valueOf(value);
        } catch (NumberFormatException ignored) {
        }
    }

    public long toLong() {
        return value;
    }
}
