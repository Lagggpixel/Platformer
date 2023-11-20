package me.lagggpixel.platformerTutorial.entities;

import me.lagggpixel.platformerTutorial.utils.HelperMethods;
import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;
import me.lagggpixel.platformerTutorial.utils.enums.PlayerStatus;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int animationTick, animationIndex;
    private PlayerStatus playerAction = PlayerStatus.IDLE;
    private boolean up, down, left, right;
    private boolean moving = false, attacking = false;
    protected final float playerSpeed = 4;
    protected final int animationSpeed = 2;
    private int[][] lvlData;
    private final float xDrawOffset = 21 * GameConstants.scale;
    private final float yDrawOffset = 4 * GameConstants.scale;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitBox(x, y, (int) (20*GameConstants.scale), (int) (28*GameConstants.scale));
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction.index()][animationIndex], (int) (hitBox.x - xDrawOffset), (int) (hitBox.y - yDrawOffset), width, height, null);
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }


    private void updatePosition() {

        moving = false;

        if (!left && !right && !up && !down) {
            return;
        }

        float xSpeed = 0, ySpeed = 0;

        if (left && !right) {
            xSpeed = -playerSpeed;
        } else if (right && !left) {
            xSpeed = playerSpeed;
        }

        if (up && !down) {
            ySpeed = -playerSpeed;
        } else if (down && !up) {
            ySpeed = playerSpeed;
        }

        if (HelperMethods.canMoveHere(hitBox.x + xSpeed, hitBox.y+ ySpeed, hitBox.width, hitBox.height, lvlData)) {
            hitBox.x += xSpeed;
            hitBox.y += ySpeed;
            moving = true;
        }
    }

    /**
     * Updates the animation tick and advances the animation index once the animation tick
     * reaches the animation speed. If the animation index reaches the maximum spirit amount
     * of the player's action, it resets the animation index to 0. If the player is currently
     * attacking, it sets the attacking flag to false.
     */
    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= playerAction.getSpiritAmount()) {
                animationIndex = 0;
                if (attacking) {
                    attacking = false;
                }
            }
        }
    }

    /**
     * Sets the animation for the player based on their current status.
     */
    private void setAnimation() {

        PlayerStatus initialAnimation = playerAction;


        if (moving) {
            playerAction = PlayerStatus.RUNNING;
        } else if (attacking) {
            playerAction = PlayerStatus.ATTACK_1;
        } else {
            playerAction = PlayerStatus.IDLE;
        }

        if (initialAnimation != playerAction) {
            animationTick = 0;
            animationIndex = 0;
        }
    }

    /**
     * Loads the animations for the player.
     */
    private void loadAnimations() {
        BufferedImage image = LoadSave.getSpritesAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[9][6];

        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = image.getSubimage(j * 64, i * 40, 64, 40);
            }
        }
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    /**
     * Resets the boolean variables for the directions of the player.
     */
    public void resetDirBooleans() {
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isAttacking() {
        return attacking;
    }
}
