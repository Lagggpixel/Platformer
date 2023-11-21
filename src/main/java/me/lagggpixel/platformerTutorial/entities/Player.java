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
    protected final float playerSpeed = 8f * GameConstants.SCALE;
    protected final int animationSpeed = 3;
    private int[][] lvlData;
    protected final float xDrawOffset = 21 * GameConstants.SCALE, yDrawOffset = 4 * GameConstants.SCALE;
    // Jumping & gravity
    private float airSpeed = 1f;
    private boolean jump;
    protected final float gravity = 4f * GameConstants.SCALE;
    protected final float jumpSpeed = -25f * GameConstants.SCALE;
    protected final float fallSpeedAfterCollision = 5f * GameConstants.SCALE;
    private boolean inAir = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitBox(x, y, (int) (20*GameConstants.SCALE), (int) (27*GameConstants.SCALE));
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g, int xLevelOffset) {
        // drawHitBox(g);
        g.drawImage(animations[playerAction.index()][animationIndex], (int) (hitBox.x - xDrawOffset) - xLevelOffset, (int) (hitBox.y - yDrawOffset), width, height, null);
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }


    private void updatePosition() {

        moving = false;

        if (jump) {
            jump();
        }

        if (!left && !right && !inAir) {
            return;
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
        }
        if (right) {
            xSpeed += playerSpeed;
        }

        if (!inAir) {
            if (!HelperMethods.isEntityOnFloor(hitBox, lvlData)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (HelperMethods.canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPosition(xSpeed);
            }
            else {
                hitBox.y = HelperMethods.getEntityYPosUnderRoofOrAboveGround(hitBox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                }
                else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPosition(xSpeed);
            }

        } else {
            updateXPosition(xSpeed);
        }
        moving = true;
    }

    private void jump() {
        if (inAir) {
            return;
        }
        airSpeed = jumpSpeed;
        inAir = true;
    }

    private void resetInAir() {
        airSpeed = 0;
        inAir = false;
    }

    private void updateXPosition(float xSpeed) {
        if (HelperMethods.canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
            hitBox.x += xSpeed;
        }
        else {
            hitBox.x = HelperMethods.getEntityXPosNextToWall(hitBox, xSpeed);
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
        }
        else {
            playerAction = PlayerStatus.IDLE;
        }

        if (inAir) {
            if (airSpeed < 0) {
                playerAction = PlayerStatus.JUMPING;
            }
            else {
                playerAction = PlayerStatus.FALLING;
            }
        }

        if (attacking) {
            playerAction = PlayerStatus.ATTACK_1;
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
        if (!HelperMethods.isEntityOnFloor(hitBox, lvlData)) {
            inAir = true;
        }
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

    public void setJump(boolean b) {
        this.jump = b;
    }
}
