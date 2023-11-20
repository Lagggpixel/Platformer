package me.lagggpixel.platformerTutorial.entities;

import me.lagggpixel.platformerTutorial.utils.enums.PlayerStatus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 2;
    private PlayerStatus playerAction = PlayerStatus.IDLE;
    private boolean up, down, left, right;
    private boolean moving = false, attacking = false;
    private float playerSpeed = 4;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction.getSpiritAmount()][animationIndex], (int) x, (int) y, 128, 80, null);
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void updatePosition() {

        moving = false;

        if (left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if (right && !left) {
            x += playerSpeed;
            moving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if (down && !up) {
            y += playerSpeed;
            moving = true;
        }
    }

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

    private void setAnimation() {

        PlayerStatus initialAnimation = playerAction;

        if (moving) {
            playerAction = PlayerStatus.RUNNING;
        }
        else if (attacking) {
            playerAction = PlayerStatus.ATTACK_1;
        }
        else {
            playerAction = PlayerStatus.IDLE;
        }

        if (initialAnimation != playerAction) {
            animationTick = 0;
            animationIndex = 0;
        }
    }


    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");
        try {
            assert is != null;
            BufferedImage image = ImageIO.read(is);

            animations = new BufferedImage[9][6];

            for (int i = 0; i < animations.length; i++) {
                for (int j = 0; j < animations[i].length; j++) {
                    animations[i][j] = image.getSubimage(j * 64, i * 40, 64, 40);
                }
            }
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

    }

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

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
}
