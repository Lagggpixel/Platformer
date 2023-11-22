package me.lagggpixel.platformerTutorial.utils.enums;

public enum PlayerStatus {
    IDLE(0, 5),
    RUNNING(1, 6),
    JUMPING(2, 3),
    FALLING(3, 1),
    ATTACK(4, 3),
    HIT(5, 4),
    DEAD(6, 8);

    private final int index;
    private final int spiritAmount;

    PlayerStatus(int index, int spiritAmount) {
        this.index = index;
        this.spiritAmount = spiritAmount;
    }

    public int getSpiritAmount() {
        return this.spiritAmount;
    }

    public int index() {
        return this.index;
    }
}
