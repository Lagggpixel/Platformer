package me.lagggpixel.platformerTutorial.entities;

import me.lagggpixel.platformerTutorial.utils.HelperMethods;
import me.lagggpixel.platformerTutorial.utils.constants.EnemyConstants;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;
import me.lagggpixel.platformerTutorial.utils.enums.Directions;

import java.awt.geom.Rectangle2D;

public abstract class Enemy extends Entity {

    protected final int enemyType;
    protected int animationIndex, enemyState = EnemyConstants.IDLE;
    protected int animationTick, animationSpeed = 2;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed, gravity = 0.1f * GameConstants.SCALE;
    protected float walkSpeed = 4f * GameConstants.SCALE;
    protected Directions walkDir = Directions.LEFT;
    protected int tileY;
    protected float attackRange = GameConstants.TILE_SIZE, visualRange = 5 * attackRange;
    protected boolean active = true;
    protected boolean attackChecked;

    // Health
    protected int maxHealth;
    protected int currentHealth;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitBox(x, y, width, height);

        maxHealth = EnemyConstants.getMaxHealth(enemyType);
        currentHealth = maxHealth;
    }

    /**
     * Update the entity's states when program is first run
     *
     * @param lvlData the level data as a 2-dimensional array
     */
    protected void firstUpdateCheck(int[][] lvlData) {
        if (!HelperMethods.isEntityOnFloor(hitBox, lvlData)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    /**
     * Updates the position of the entity if it is in the air.
     *
     * @param lvlData the level data representing the game level
     */
    protected void updateInAir(int[][] lvlData) {
        if (HelperMethods.canMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, lvlData)) {
            hitBox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitBox.y = HelperMethods.getEntityYPosUnderRoofOrAboveGround(hitBox, fallSpeed);
            fallSpeed = 0f;
            tileY = (int) Math.round(hitBox.y / GameConstants.TILE_SIZE);
        }
    }

    /**
     * Moves the object horizontally based on the walk direction and level data.
     *
     * @param lvlData the level data representing the game level
     */
    protected void move(int[][] lvlData) {
        float xSpeed = 0;

        if (walkDir == Directions.LEFT) {
            xSpeed -= walkSpeed;
        }
        if (walkDir == Directions.RIGHT) {
            xSpeed += walkSpeed;
        }

        if (HelperMethods.canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
            if (HelperMethods.isFloor(hitBox, xSpeed, lvlData)) {
                hitBox.x += xSpeed;
                return;
            }
        }

        changeWalkDir();
    }

    /**
     * Sets the new state for the enemy.
     *
     * @param newState the new state to set for the enemy
     */
    protected void newState(int newState) {
        this.enemyState = newState;
        animationTick = 0;
        animationIndex = 0;
    }

    protected boolean isActive() {
        return active;
    }

    /**
     * Determines if the player is visible to the current object.
     *
     * @param  lvlData   the level data containing the map information
     * @param  player    the player object to check visibility against
     * @return           true if the player is visible, false otherwise
     */
    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) Math.round(player.getHitBox().getY() / GameConstants.TILE_SIZE);
        return playerTileY == tileY && isPlayerInRange(player) && HelperMethods.isSightClear(lvlData, hitBox, player.getHitBox(), tileY);
    }

    /**
     * Turns the character towards the specified player.
     *
     * @param  player  the player to turn towards
     */
    protected void turnTowardsPlayer(Player player) {
        if (player.getHitBox().x > hitBox.x) {
            walkDir = Directions.RIGHT;
        } else {
            walkDir = Directions.LEFT;
        }
    }

    /**
     * Determines if a player is within visual range.
     *
     * @param  player  the player to check
     * @return         true if the player is within range, false otherwise
     */
    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.round(Math.abs(player.getHitBox().getX() - hitBox.x));
        return absValue < visualRange;
    }

    /**
     * Determines if a player is close enough for attack.
     *
     * @param  player  the player to check
     * @return         true if the player is close enough for attack, false otherwise
     */
    protected boolean isPlayerCloseForAttack(Player player) {
        if (tileY != (int) Math.round(player.getHitBox().getY() / GameConstants.TILE_SIZE)) {
            return false;
        }
        int absValue = (int) Math.round(Math.abs(player.getHitBox().getX() - hitBox.x));
        return absValue < attackRange;
    }

    /**
     * Updates the animation tick for the function.
     */
    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= EnemyConstants.getSpriteAmount(enemyType, enemyState)) {
                animationIndex = 0;

                switch (enemyState) {
                    case EnemyConstants.ATTACK, EnemyConstants.HIT -> newState(EnemyConstants.IDLE);
                    case EnemyConstants.DEAD -> active = false;
                }
            }
        }
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    protected void changeWalkDir() {
        if (walkDir == Directions.LEFT) {
            walkDir = Directions.RIGHT;
        } else {
            walkDir = Directions.LEFT;
        }
    }

    public void hurt(int i) {
        currentHealth -= i;
        if (currentHealth <= 0) {
            newState(EnemyConstants.DEAD);
        }
        else {
            newState(EnemyConstants.HIT);
        }
    }

    protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitBox)) {
            player.changeHealth(-EnemyConstants.getDamage(enemyType));
        }
        attackChecked = true;
    }

    public void resetEnemy() {
        // reset enemy location
        hitBox.x = x;
        hitBox.y = y;

        firstUpdate = true;
        currentHealth = maxHealth;
        newState(EnemyConstants.IDLE);
        active = true;
        fallSpeed = 0;
    }
}
