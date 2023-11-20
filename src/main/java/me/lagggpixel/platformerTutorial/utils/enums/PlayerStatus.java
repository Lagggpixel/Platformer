package me.lagggpixel.platformerTutorial.utils.enums;

public enum PlayerStatus {
    IDLE(5),
    RUNNING(6),
    JUMPING(3),
    FALLING(1),
    GROUND(2),
    HIT(4),
    ATTACK_1(3),
    ATTACK_JUMP_1(3),
    ATTACK_JUMP_2(3);

    private final int spiritAmount;

    PlayerStatus(int spiritAmount) {
        this.spiritAmount = spiritAmount;
    }

    public int getSpiritAmount() {
        return this.spiritAmount;
    }
}
