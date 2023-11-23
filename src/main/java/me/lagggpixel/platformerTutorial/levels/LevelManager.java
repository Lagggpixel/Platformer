package me.lagggpixel.platformerTutorial.levels;

import me.lagggpixel.platformerTutorial.Game;
import me.lagggpixel.platformerTutorial.gameStates.enums.GameState;
import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {

    protected final Game game;
    private BufferedImage[] levelSprite;
    private final ArrayList<Level> levels;
    private int levelIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    private void buildAllLevels() {
        BufferedImage[] levelImages = LoadSave.getAllLevels();
        for (BufferedImage level : levelImages) {
            levels.add(new Level(level));
        }
    }

    private void importOutsideSprites() {
        BufferedImage image = LoadSave.getSpritesAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                levelSprite[i * 12 + j] = image.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }


    public void update() {
    }

    public void render(Graphics g, int xLevelOffset) {
        for (int j = 0; j< GameConstants.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i< levels.get(levelIndex).getLvlData()[0].length; i++) {
                int index = levels.get(levelIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index],
                        i*GameConstants.TILE_SIZE -xLevelOffset, j*GameConstants.TILE_SIZE,
                        GameConstants.TILE_SIZE, GameConstants.TILE_SIZE,
                        null);
            }
        }
    }

    public Level getCurrentLvlData() {
        return levels.get(levelIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }

    public void loadNextLevel() {
        levelIndex++;
        if (levelIndex >= levels.size()) {
            levelIndex = 0;
            GameState.state = GameState.MENU;
            // TODO: add win screen
        }

        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLvlData());
        game.getPlaying().setXLevelOffset(newLevel.getMaxLevelOffsetX());
    }
}
