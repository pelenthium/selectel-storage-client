package com.github.pelenthium.selectel.utils;

public class IntString {

    private int value;

    public IntString(String value) {
        try {
            this.value = Integer.valueOf(value);
        } catch (NumberFormatException ignored) {
        }
    }

    public int toInt() {
        return value;
    }
}
