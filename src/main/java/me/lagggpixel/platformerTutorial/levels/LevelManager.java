package me.lagggpixel.platformerTutorial.levels;

import me.lagggpixel.platformerTutorial.Game;
import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {

    protected final Game game;
    private BufferedImage[] levelSprite;
    private final Level level;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        level = new Level(LoadSave.getLevelData());
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
            for (int i = 0; i< level.getLvlData()[0].length; i++) {
                int index = level.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index],
                        i*GameConstants.TILE_SIZE -xLevelOffset, j*GameConstants.TILE_SIZE,
                        GameConstants.TILE_SIZE, GameConstants.TILE_SIZE,
                        null);
            }
        }
    }

    public Level getCurrentLvlData() {
        return level;
    }
}
