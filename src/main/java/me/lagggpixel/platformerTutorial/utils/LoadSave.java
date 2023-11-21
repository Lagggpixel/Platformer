package me.lagggpixel.platformerTutorial.utils;

import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";

    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";

    public static final String PAUSE_BACKGROUND = "pause_background.png";
    public static final String SOUND_BUTTONS = "sound_buttons.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";

    public static BufferedImage getSpritesAtlas(String filename) {
        BufferedImage image;
        InputStream is = LoadSave.class.getResourceAsStream("/" + filename);
        try {
            assert is != null;
            image = ImageIO.read(is);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // close import stream
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return image;
    }

    public static int[][] getLevelData() {
        int[][] lvlData = new int[GameConstants.tiles_in_height][GameConstants.tiles_in_width];
        BufferedImage image = LoadSave.getSpritesAtlas(LoadSave.LEVEL_ONE_DATA);
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

}
