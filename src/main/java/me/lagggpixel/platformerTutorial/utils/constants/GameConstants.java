package me.lagggpixel.platformerTutorial.utils.constants;

public class GameConstants {

    private static final int tiles_default_size = 32;
    public static final float scale = 2f;
    public static final int tiles_in_width = 26;
    public static final int tiles_in_height = 14;
    public static final int tile_size = (int) (tiles_default_size * scale);
    public static final int width = tile_size * tiles_in_width;
    public static final int height = tile_size * tiles_in_height;

    public static final int fps = 60, tps = 20;

}
