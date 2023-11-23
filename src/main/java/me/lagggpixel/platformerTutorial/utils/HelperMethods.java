package me.lagggpixel.platformerTutorial.utils;

import me.lagggpixel.platformerTutorial.entities.Crabby;
import me.lagggpixel.platformerTutorial.utils.constants.EnemyConstants;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class HelperMethods {

    /**
     * Determines if an object can move to a specified position on the level.
     *
     * @param  x       the x-coordinate of the position
     * @param  y       the y-coordinate of the position
     * @param  width   the width of the object
     * @param  height  the height of the object
     * @param  lvlData the level data
     * @return         true if the object can move to the specified position, false otherwise
     */
    public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {

        return !isSolid(x, y, lvlData)
                && !isSolid(x + width, y, lvlData)
                && !isSolid(x, y + height, lvlData)
                && !isSolid(x + width, y + height, lvlData);

    }

    /**
     * Determines if a given point on the level map is solid or not.
     *
     * @param  x         the x-coordinate of the point
     * @param  y         the y-coordinate of the point
     * @param  lvlData   the level map data
     * @return           true if the point is solid, false otherwise
     */
    private static boolean isSolid(float x, float y, int[][] lvlData) {
        int maxWidth = GameConstants.TILE_SIZE * (lvlData[0].length);
        if (x < 0 || y < 0 || x >= maxWidth || y >= GameConstants.HEIGHT) {
            return true;
        }

        float xIndex = x / GameConstants.TILE_SIZE;
        float yIndex = y / GameConstants.TILE_SIZE;

        return isTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    /**
     * Determines if a tile at the specified coordinates is solid.
     *
     * @param  xTile     the x-coordinate of the tile
     * @param  yTile     the y-coordinate of the tile
     * @param  lvlData   the level data containing the tile values
     * @return           true if the tile is solid, false otherwise
     */
    private static boolean isTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        return value < 48 && value >= 0 && value != 11;
    }

    /**
     * Calculates the next x position of an entity next to a wall, based on its hitbox and x speed.
     *
     * @param  hitBox   the hitbox of the entity
     * @param  xSpeed   the x speed of the entity
     * @return          the next x position of the entity next to the wall
     */
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

    /**
     * Returns the Y position of an entity either under a roof or above the ground.
     *
     * @param  hitBox   the hitbox of the entity
     * @param  airSpeed the airspeed of the entity
     * @return          the Y position of the entity
     */
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

    /**
     * Determines if an entity is on the floor.
     *
     * @param  hitBox   the hit box of the entity
     * @param  lvlData  the level data
     * @return          true if the entity is on the floor, false otherwise
     */
    public static boolean isEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData) {

        return isSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData) || isSolid(hitBox.x + hitBox.width + 1, hitBox.y, lvlData);
    }

    /**
     * Determines if the given hitBox is on the floor.
     *
     * @param  hitBox   the hit box to check
     * @param  xSpeed   the horizontal speed
     * @param  lvlData  the level data
     * @return          true if the hitBox is on the floor, false otherwise
     */
    public static boolean isFloor(Rectangle2D.Float hitBox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0) {
            return isSolid(hitBox.x + hitBox.width + xSpeed, hitBox.y + hitBox.height + 1, lvlData);
        } else {
            return isSolid(hitBox.x + xSpeed, hitBox.y + hitBox.height + 1, lvlData);
        }

    }

    /**
     * Determines if all the tiles in a given range are walkable.
     *
     * @param  xStart   the starting x coordinate
     * @param  xEnd     the ending x coordinate
     * @param  y        the y coordinate
     * @param  lvlData  the level data containing tile information
     * @return          true if all tiles in the range are walkable, false otherwise
     */
    private static boolean isAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (isTileSolid(xStart + i, y, lvlData)) {
                return false;
            }
            if (!isTileSolid(xStart + i, y + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the sight between two hitboxes is clear in a given level.
     *
     * @param  lvlData        the level data in a 2D array
     * @param  firstHitbox    the hitbox of the first object
     * @param  secondHitbox   the hitbox of the second object
     * @param  tileY          the y-coordinate of the tile
     * @return                true if the sight is clear, false otherwise
     */
    public static boolean isSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int tileY) {
        int firstXTile = Math.round(firstHitbox.x / GameConstants.TILE_SIZE);
        int secondXTile = Math.round(secondHitbox.x / GameConstants.TILE_SIZE);

        if (firstXTile > secondXTile) {
            return isAllTilesWalkable(secondXTile, firstXTile, tileY, lvlData);
        } else {
            return isAllTilesWalkable(firstXTile, secondXTile, tileY, lvlData);
        }
    }

    /**
     * Given a BufferedImage, this function converts the image into a 2D array of integers
     * representing the level data. Each pixel in the image is converted to its red value,
     * and if the red value is greater than or equal to 48, the corresponding element in
     * the level data array is set to 0, otherwise it is set to the red value. The resulting
     * 2D array is then returned.
     *
     * @param  image  the BufferedImage to convert to level data
     * @return        the 2D array of integers representing the level data
     */
    public static int[][] getLevelData(BufferedImage image) {
        int[][] lvlData = new int[image.getHeight()][image.getWidth()];
        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                int value = new Color(image.getRGB(i, j)).getRed();
                if (value >= 48) {
                    value = 0;
                }
                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }

    /**
     * Retrieves a list of Crabby objects from the given BufferedImage.
     *
     * @param  image  the BufferedImage to search for crabs
     * @return        an ArrayList of Crabby objects found in the image
     */
    public static ArrayList<Crabby> getCrabs(BufferedImage image) {
        ArrayList<Crabby> crabs = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == EnemyConstants.CRABBY) {
                    crabs.add(new Crabby(i * GameConstants.TILE_SIZE, j * GameConstants.TILE_SIZE));
                }
            }
        }
        return crabs;
    }

    /**
     * Get the spawn point for the player in the game.
     *
     * @param  image  the image to search for the spawn point
     * @return        the spawn point coordinates as a Point object
     */
    public static Point getPlayerSpawn(BufferedImage image) {
        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100) {
                    return new Point(i * GameConstants.TILE_SIZE, j * GameConstants.TILE_SIZE);
                }
            }
        }
        return new Point(100, 100);
    }
}
