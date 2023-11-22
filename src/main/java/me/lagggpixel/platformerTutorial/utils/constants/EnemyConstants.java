package me.lagggpixel.platformerTutorial.utils.constants;

public class EnemyConstants {

    public static final int CRABBY = 0;

    public static final int IDLE = 0;
    public static final int RUNNING = 1;
    public static final int ATTACK = 2;
    public static final int HIT = 3;
    public static final int DEAD = 4;

    public static final int CRABBY_WIDTH_DEFAULT = 72;
    public static final int CRABBY_HEIGHT_DEFAULT = 32;
    public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * GameConstants.SCALE);
    public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * GameConstants.SCALE);
    public static final int CRABBY_HITBOX_WIDTH = (int) (22 * GameConstants.SCALE);
    public static final int CRABBY_HITBOX_HEIGHT = (int) (19 * GameConstants.SCALE);

    public static final int CRABBY_DRAW_OFFSET_X = (int) (26 * GameConstants.SCALE);
    public static final int CRABBY_DRAW_OFFSET_Y = (int) (9 * GameConstants.SCALE);

    public static int getSpriteAmount(int enemyType, int enemyState) {
        switch (enemyType) {
            case CRABBY:
                return switch (enemyState) {
                    case IDLE -> 9;
                    case RUNNING -> 6;
                    case ATTACK -> 7;
                    case HIT -> 4;
                    case DEAD -> 5;
                    default -> 0;
                };
            default:
                return 0;
        }
    }

    public static int getMaxHealth(int enemyType) {
        switch (enemyType) {
            case CRABBY -> {
                return 10;
            }
            default -> {
                return 1;
            }
        }
    }

    public static int getDamage(int enemyType) {
        switch (enemyType) {
            case CRABBY -> {
                return 15;
            }
            default -> {
                return 0;
            }
        }
    }
}
