package me.lagggpixel.platformerTutorial.utils.enums;

public enum Directions {
    IDLE(0),
    UP(1),
    DOWN(2),
    LEFT(3),
    RIGHT(4);

    private final int value;

    Directions(int value) {
        this.value = value;
    }
}