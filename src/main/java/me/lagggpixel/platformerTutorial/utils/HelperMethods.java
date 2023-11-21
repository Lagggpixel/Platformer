package me.lagggpixel.platformerTutorial.utils;

import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.geom.Rectangle2D;

public class HelperMethods {

    public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {

        if (isSolid(x, y, lvlData)) {
            return false;
        }
        if (isSolid(x + width, y, lvlData)) {
            return false;
        }
        if (isSolid(x, y + height, lvlData)) {
            return false;
        }
        if (isSolid(x + width, y + height, lvlData)) {
            return false;
        }

        return true;

    }

    private static boolean isSolid(float x, float y, int[][] lvlData) {

        if (x < 0 || y < 0 || x >= GameConstants.width || y >= GameConstants.height) {
            return true;
        }

        float xIndex = x / GameConstants.tile_size;
        float yIndex = y / GameConstants.tile_size;

        int value = lvlData[(int) yIndex][(int) xIndex];

        if (value >= 48 || value < 0 || value == 11) {
            return false;
        }

        return true;
    }

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = Math.round(hitBox.x / GameConstants.tile_size);
        if (xSpeed > 0) {
            // Right motion
            int tileXPosition = currentTile * GameConstants.tile_size;
            int xOffset = (int) (GameConstants.tile_size - hitBox.width);
            return tileXPosition + xOffset - 1;
        } else {
            // Left motion
            return currentTile * GameConstants.tile_size;
        }
    }

    public static float getEntityYPosUnderRoofOrAboveGround(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = Math.round(hitBox.y / GameConstants.tile_size);
        if (airSpeed > 0) {
            // Downward motion
            int tileYPosition = currentTile * GameConstants.tile_size;
            int yOffset = (int) (GameConstants.tile_size - hitBox.height);
            return tileYPosition + yOffset - 1;
        } else {
            // Upward motion
            return currentTile * GameConstants.tile_size;
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
