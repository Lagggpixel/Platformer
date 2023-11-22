package me.lagggpixel.platformerTutorial.entities;

import me.lagggpixel.platformerTutorial.Game;
import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.EnemyConstants;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {

    private final Game game;

    private BufferedImage[][] crabbyImages;
    private ArrayList<Crabby> crabbies = new ArrayList<>();

    public EnemyManager(Game game) {
        this.game = game;

        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies() {
        crabbies = LoadSave.getCrabs();
    }

    public void update(int[][] lvlData, Player player) {
        for (Crabby c : crabbies) {
            if (c.isActive()) {
                c.update(lvlData, player);
            }
        }
    }

    public void render(Graphics g, int xLevelOffset) {
        renderCrabs(g, xLevelOffset);
    }

    private void renderCrabs(Graphics g, int xLevelOffset) {
        for (Crabby c : crabbies) {
            if (c.isActive()) {
                // c.renderHitBox(g, xLevelOffset);
                // c.renderAttackBox(g, xLevelOffset);
                g.drawImage(crabbyImages[c.getEnemyState()][c.getAnimationIndex()],
                        (int) c.getHitBox().x - EnemyConstants.CRABBY_DRAW_OFFSET_X - xLevelOffset + c.flipX(),
                        (int) c.getHitBox().y - EnemyConstants.CRABBY_DRAW_OFFSET_Y,
                        EnemyConstants.CRABBY_WIDTH * c.flipW(), EnemyConstants.CRABBY_HEIGHT, null);
            }
        }
    }


    private void loadEnemyImages() {
        crabbyImages = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.getSpritesAtlas(LoadSave.CRABBY_SPRITE);
        for (int j = 0; j < crabbyImages.length; j++) {
            for (int i = 0; i < crabbyImages[j].length; i++) {
                crabbyImages[j][i] = temp.getSubimage(i * EnemyConstants.CRABBY_WIDTH_DEFAULT, j * EnemyConstants.CRABBY_HEIGHT_DEFAULT, EnemyConstants.CRABBY_WIDTH_DEFAULT, EnemyConstants.CRABBY_HEIGHT_DEFAULT);
            }
        }

    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Crabby crabby: crabbies) {
            if (crabby.isActive()) {
                if (attackBox.intersects(crabby.getHitBox())) {
                    crabby.hurt(Player.attackDamage);
                    return;
                }
            }
        }
    }

    public void resetAllEnemies() {
        for (Crabby crabby : crabbies) {
            crabby.resetEnemy();
        }
    }
}
