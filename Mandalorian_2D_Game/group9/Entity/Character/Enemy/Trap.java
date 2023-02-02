package ca.sfu.group9.Entity.Character.Enemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Trap extends Enemy{

    BufferedImage landmine;
    BufferedImage landmine_exploded;

    private int deathAnimationCounter;
    private int DEATH_ANIMATION_DURATION;

    public Trap() {
        this.type = "Trap";
        this.setContactDamageAmount(50);
        DEATH_ANIMATION_DURATION = 3;
}

    public void getEntityImage() {

        try {

            landmine = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/landmine.png"));
            landmine_exploded = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/landmine_exploded.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void killEntity() {
        this.isDead = true;
        deathAnimationCounter = 0;
    }

    public void update() {
        deathAnimationCounter++;

        // plays an animation before destroying the trap
        if(isDead == true && deathAnimationCounter >= DEATH_ANIMATION_DURATION) {

            this.removeOnNextTick = true;
        }
    }

    @Override
    protected void getFrameImage() {
        if(isDead == true) {
            image = landmine_exploded;
        }
        else {
            image = landmine;
        }
    }
}