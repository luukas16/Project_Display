package ca.sfu.group9.Entity.Character.Enemy;

import ca.sfu.group9.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Projectile extends Enemy{

    int MOVE_DELAY;
    int moveCounter;

    BufferedImage up1, down1, left1, right1;

    public Projectile(FacingDirection direction) {
        this.type = "Projectile";
        this.setContactDamageAmount(20);
        this.facingDirection = direction;

        MOVE_DELAY = 3;
        moveCounter = 0;
    }

    public void getEntityImage() {

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/blaster_up.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/blaster_down.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/blaster_left.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/blaster_right.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Moves the projectile in the direction it is facing until it hits a barrier or the edge of the screen.
     * @param gp GamePanel
     * @param value Direction of the projectile
     */
    private void moveProjectile(GamePanel gp, FacingDirection value) {

        switch (value) {
            case UP:

                if (this.positionY - 1 >= 0 && (gp.gameMap.getMapTile(this.getPositionX(), this.getPositionY() - 1).isPassable())) {
                    positionY -= 1;
                }
                else { this.removeOnNextTick = true; }
                break;

            case DOWN:

                if (positionY + 1 < gp.gameMap.getMapSizeY() && (gp.gameMap.getMapTile(this.getPositionX(), this.getPositionY() + 1).isPassable())) {
                    this.positionY += 1;
                }
                else { this.removeOnNextTick = true; }
                break;

            case LEFT:

                if (positionX - 1 >= 0 && (gp.gameMap.getMapTile(this.getPositionX() - 1, this.getPositionY()).isPassable())) {
                    this.positionX -= 1;
                }
                else { this.removeOnNextTick = true; }
                break;

            case RIGHT:

                if (positionX + 1 < gp.gameMap.getMapSizeX() && (gp.gameMap.getMapTile(this.getPositionX() + 1, this.getPositionY()).isPassable())) {
                    this.positionX += 1;
                }
                else { this.removeOnNextTick = true; }
                break;

            default:
                break;
        }
    }


    public void update() {
        GamePanel gp = GamePanel.getGamePanel();
        moveCounter++;

        updateLastPosition();

        if (moveCounter % MOVE_DELAY == 0){
            moveProjectile(gp, this.facingDirection);
        }
    }

    @Override
    protected void getFrameImage() {
        switch(this.facingDirection) {
            case UP:
                image = up1;
                break;
            case DOWN:
                image = down1;
                break;
            case LEFT:
                image = left1;
                break;
            case RIGHT:
                image = right1;
                break;
        }
    }
}
