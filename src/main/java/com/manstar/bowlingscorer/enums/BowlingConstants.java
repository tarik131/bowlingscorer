package com.manstar.bowlingscorer.enums;

/**
 * Hold constant that ten pin bowling program uses.
 */
public enum BowlingConstants {

    STARTING_FRAME(1),
    MAX_PINS(10),
    LAST_ROUND(10);

    private final int value;

    BowlingConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
