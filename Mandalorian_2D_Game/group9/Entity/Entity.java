package ca.sfu.group9.Entity;

import ca.sfu.group9.GamePanel;
import ca.sfu.group9.Map.GameMap;
import ca.sfu.group9.Position;
import ca.sfu.group9.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    protected int positionX, positionY;
    /**
     * Entity type name.
     */
    protected String type;
    /**
     * Sprite for animation frames.
     */
    public BufferedImage image;
    /**
     * Boolean to decide if the entity should be removed by the game on the next tick.
     */
    public boolean removeOnNextTick = false;

    protected Position lastPosition = new Position(-1, -1);

    protected boolean isDead;

    public Entity() {
        this.type = "Entity";

        this.isDead = false;

        getEntityImage();
    }

    //getters and setters
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getType() {
        return this.type;
    }

    public void killEntity() {
        this.removeOnNextTick = true;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void setLastPosition(Position position) { this.lastPosition = position; }

    public void updateLastPosition() { lastPosition = new Position(getPositionX(), getPositionY()); }

    public Position getLastPosition() { return lastPosition; }

    /**
     * Loads the sprites for the Entity.
     */
    public void getEntityImage() {

        try {

            image = ImageIO.read(getClass().getResourceAsStream("/sprites/player/player_up1.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Contains all actions the entity will take every tick.
     */
    public void update() {
        // noting to update
    }

    /**
     * Chooses the current animation frame for an entity
     */
    protected void getFrameImage() {

    }

    /**
     * Chooses the animation frame and draws it.
     * @param g2 Screen graphics object
     */
    public void draw(Graphics2D g2) {
        getFrameImage();

        g2.drawImage(image, positionX * GamePanel.getTileSize(), positionY * GamePanel.getTileSize() + UI.getUiHeight(), GamePanel.getTileSize(), GamePanel.getTileSize(), null);
    }
}
