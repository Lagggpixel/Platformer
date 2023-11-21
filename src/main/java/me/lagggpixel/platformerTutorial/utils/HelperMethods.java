package me.lagggpixel.platformerTutorial.utils;

import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.geom.Rectangle2D;

public class HelperMethods {

    public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {

        return !isSolid(x, y, lvlData)
                && !isSolid(x + width, y, lvlData)
                && !isSolid(x, y + height, lvlData)
                && !isSolid(x + width, y + height, lvlData);

    }

    private static boolean isSolid(float x, float y, int[][] lvlData) {
        int maxWidth = GameConstants.TILE_SIZE * (lvlData[0].length);
        if (x < 0 || y < 0 || x >= maxWidth || y >= GameConstants.HEIGHT) {
            return true;
        }

        float xIndex = x / GameConstants.TILE_SIZE;
        float yIndex = y / GameConstants.TILE_SIZE;

        int value = lvlData[(int) yIndex][(int) xIndex];

        return value < 48 && value >= 0 && value != 11;
    }

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = Math.round(hitBox.x / GameConstants.TILE_SIZE);
        if (xSpeed > 0) {
            // Right motion
            int tileXPosition = currentTile * GameConstants.TILE_SIZE;
            int xOffset = (int) (GameConstants.TILE_SIZE - hitBox.width);
            return tileXPosition + xOffset - 1;
        } else {
            // Left motion
            return currentTile * GameConstants.TILE_SIZE;
        }
    }

    public static float getEntityYPosUnderRoofOrAboveGround(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = Math.round(hitBox.y / GameConstants.TILE_SIZE);
        if (airSpeed > 0) {
            // Downward motion
            int tileYPosition = currentTile * GameConstants.TILE_SIZE;
            int yOffset = (int) (GameConstants.TILE_SIZE - hitBox.height);
            return tileYPosition + yOffset - 1;
        } else {
            // Upward motion
            return currentTile * GameConstants.TILE_SIZE;
        }
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData) {

        if (isSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData)) {
            return true;
        }

        if (isSolid(hitBox.x + hitBox.width + 1, hitBox.y, lvlData)) {
            return true;
        }

        return false;
    }
}
