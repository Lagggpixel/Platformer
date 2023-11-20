package me.lagggpixel.platformerTutorial.utils;

import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

public class HelperMethods {

    public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {

        if (isSolid(x, y, lvlData)) {
            return false;
        }
        if (isSolid(x+width-1, y, lvlData)) {
            return false;
        }
        if (isSolid(x, y+height-1, lvlData)) {
            return false;
        }
        if (isSolid(x+width-1, y+height-1, lvlData)) {
            return false;
        }

        return true;

    }

    private static boolean isSolid(float x, float y, int[][] lvlData) {

        if (x < 0 || y < 0 || x >= GameConstants.width || y >= GameConstants.height) {
            return true;
        }

        float xIndex = x / GameConstants.title_size;
        float yIndex = y / GameConstants.title_size;

        int value = lvlData[(int) yIndex][(int) xIndex];

        if (value >= 48 || value < 0 || value == 11) {
            return false;
        }

        return true;
    }
}
