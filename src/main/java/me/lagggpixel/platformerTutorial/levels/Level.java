package me.lagggpixel.platformerTutorial.levels;

import me.lagggpixel.platformerTutorial.entities.Crabby;
import me.lagggpixel.platformerTutorial.utils.HelperMethods;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level {

    private BufferedImage image;
    private int[][] lvlData;
    private ArrayList<Crabby> crabbies;
    private int levelTilesWide;
    private int maxTilesOffset;
    private int maxLevelOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage image) {
        this.image = image;
        
        initLevelData();
        initEnemies();
        initLevelOffset();
        initPlayerSpawn();
    }

    private void initPlayerSpawn() {
        playerSpawn = HelperMethods.getPlayerSpawn(image);
    }

    private void initLevelData() {
        lvlData = HelperMethods.getLevelData(image);
    }

    private void initEnemies() {
        crabbies = HelperMethods.getCrabs(image);
    }

    private void initLevelOffset() {
        levelTilesWide = image.getWidth();
        maxTilesOffset = levelTilesWide - GameConstants.TILES_IN_WIDTH;
        maxLevelOffsetX = GameConstants.TILE_SIZE * maxTilesOffset;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLvlData() {
        return lvlData;
    }

    public int getMaxLevelOffsetX() {
        return maxLevelOffsetX;
    }

    public ArrayList<Crabby> getCrabbies() {
        return crabbies;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }
}