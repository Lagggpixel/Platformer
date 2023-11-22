package me.lagggpixel.platformerTutorial.entities;

import me.lagggpixel.platformerTutorial.utils.constants.EnemyConstants;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;
import me.lagggpixel.platformerTutorial.utils.enums.Directions;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Crabby extends Enemy {

    // Attack box
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Crabby(float x, float y) {
        super(x, y, EnemyConstants.CRABBY_WIDTH, EnemyConstants.CRABBY_HEIGHT, EnemyConstants.CRABBY);
        initHitBox(x, y, EnemyConstants.CRABBY_HITBOX_WIDTH, EnemyConstants.CRABBY_HITBOX_HEIGHT);
        initAttackBox();
    }

    public void update(int[][] lvlData, Player player) {
        updateBehaviour(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitBox.x - attackBoxOffsetX;
        attackBox.y = hitBox.y;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (82 * GameConstants.SCALE), (int) (19 * GameConstants.SCALE));
        attackBoxOffsetX = (int)(GameConstants.SCALE * 30);
    }

    private void updateBehaviour(int[][] lvlData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }

        if (inAir) {
            updateInAir(lvlData);
        } else {
            switch (enemyState) {
                case EnemyConstants.ATTACK:
                    if (animationIndex == 0) {
                        attackChecked = false;
                    }
                    if (animationIndex == 3 && !attackChecked) {
                        checkEnemyHit(attackBox, player);
                    }
                    break;
                case EnemyConstants.IDLE:
                    newState(EnemyConstants.RUNNING);
                    break;
                case EnemyConstants.RUNNING:
                    if (canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                    }
                    if (isPlayerCloseForAttack(player)) {
                        newState(EnemyConstants.ATTACK);
                    }
                    move(lvlData);
                    break;
                case EnemyConstants.HIT:
                    break;
                case EnemyConstants.DEAD:
                    break;
            }
        }
    }

    public void renderAttackBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) (attackBox.x - xLevelOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public int flipX() {
        if (walkDir == Directions.RIGHT) {
            return width;
        }
        else {
            return 0;
        }
    }

    public int flipW() {
        if (walkDir == Directions.RIGHT) {
            return -1;
        }
        else {
            return 1;
        }
    }

}
