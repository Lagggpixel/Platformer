package me.lagggpixel.platformerTutorial.entities;

import me.lagggpixel.platformerTutorial.Game;
import me.lagggpixel.platformerTutorial.utils.HelperMethods;
import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;
import me.lagggpixel.platformerTutorial.utils.enums.PlayerStatus;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private final Game game;

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

    // Status bar ui
    private BufferedImage statusBarImage;

    private int statusBarWidth = (int) (192 * GameConstants.SCALE);
    private int statusBarHeight = (int) (58 * GameConstants.SCALE);
    private int statusBarX = (int) (10 * GameConstants.SCALE);
    private int statusBarY = (int) (10 * GameConstants.SCALE);

    private int healthBarWidth = (int) (150 * GameConstants.SCALE);
    private int healthBarHeight = (int) (4 * GameConstants.SCALE);
    private int healthBarXStart = (int) (34 * GameConstants.SCALE);
    private int healthBarYStart = (int) (14 * GameConstants.SCALE);

    private final int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    // Attack box
    private Rectangle2D.Float attackBox;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked = false;

    public static final int attackDamage = 100;

    public Player(Game game, float x, float y, int width, int height) {
        super(x, y, width, height);

        this.game = game;

        loadAnimations();
        initHitBox(x, y, (int) (20*GameConstants.SCALE), (int) (27*GameConstants.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (20*GameConstants.SCALE), (int) (20*GameConstants.SCALE));
    }

    public void update() {
        updateHealthBar();
        if (currentHealth <= 0) {
            game.getPlaying().setGameOver(true);
            return;
        }
        updateAttackBox();

        if (attacking) {
            checkAttack();
        }
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void setSpawn(Point point) {
        this.x = point.x;
        this.y = point.y;
        hitBox.x = this.x;
        hitBox.y = this.y;
    }

    private void checkAttack() {
        if (attackChecked || animationIndex != 1) {
            return;
        }
        attackChecked = true;

        game.getPlaying().checkEnemyHit(attackBox);
    }

    private void updateAttackBox() {
        if (left) {
            attackBox.x = hitBox.x - attackBox.width - (int) (10*GameConstants.SCALE);
        } else if (right) {
            attackBox.x = hitBox.x + hitBox.width + (int) (10*GameConstants.SCALE);
        }

        attackBox.y = hitBox.y + (int) (10*GameConstants.SCALE);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((float) currentHealth / maxHealth * healthBarWidth);
    }

    public void render(Graphics g, int xLevelOffset) {
        // renderHitBox(g, xLevelOffset);
        g.drawImage(animations[playerAction.index()][animationIndex],
                (int) (hitBox.x - xDrawOffset) - xLevelOffset + flipX,
                (int) (hitBox.y - yDrawOffset),
                width * flipW, height, null);
        // renderAttackBox(g, xLevelOffset);
        renderUI(g);
    }

    private void renderAttackBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLevelOffset), (int) (attackBox.y - yDrawOffset), (int) (attackBox.width), (int) (attackBox.height));
    }

    private void renderUI(Graphics g) {
        g.drawImage(statusBarImage, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
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
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
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

    /**
     * Updates the x position of the object based on the given x speed.
     *
     * @param  xSpeed  the amount by which to update the x position
     */
    private void updateXPosition(float xSpeed) {
        if (HelperMethods.canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
            hitBox.x += xSpeed;
        }
        else {
            hitBox.x = HelperMethods.getEntityXPosNextToWall(hitBox, xSpeed);
        }
    }

    public void changeHealth(int amount) {
        currentHealth += amount;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
        if (currentHealth <= 0) {
            currentHealth = 0;
            game.getPlaying().setGameOver(true);
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
                attackChecked = false;
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
            playerAction = PlayerStatus.ATTACK;
            if (initialAnimation != PlayerStatus.ATTACK) {
                animationIndex = 1;
                animationTick = 0;
                return;
            }
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

        animations = new BufferedImage[7][8];

        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = image.getSubimage(j * 64, i * 40, 64, 40);
            }
        }

        statusBarImage = LoadSave.getSpritesAtlas(LoadSave.STATUS_BAR);
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!HelperMethods.isEntityOnFloor(hitBox, lvlData)) {
            inAir = true;
        }
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        playerAction = PlayerStatus.IDLE;
        currentHealth = maxHealth;

        // reset player location
        hitBox.x = x;
        hitBox.y = y;

        attackBox.x = hitBox.x + hitBox.width + (int) (10*GameConstants.SCALE);

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
