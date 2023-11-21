package me.lagggpixel.platformerTutorial.levels;

import me.lagggpixel.platformerTutorial.Game;
import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {

    private final Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levelOne = new Level(LoadSave.getLevelData());
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

    public void render(Graphics g) {
        for (int j=0; j< GameConstants.tiles_in_height; j++) {
            for (int i=0; i< GameConstants.tiles_in_width; i++) {
                int index = levelOne.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index],
                        i*GameConstants.tile_size, j*GameConstants.tile_size,
                        GameConstants.tile_size, GameConstants.tile_size,
                        null);
            }
        }
    }

    public Level getCurrentLvlData() {
        return levelOne;
    }
}
