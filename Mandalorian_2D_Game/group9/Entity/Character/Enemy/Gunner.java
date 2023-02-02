package ca.sfu.group9.Entity.Character.Enemy;

import ca.sfu.group9.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Gunner extends Enemy{

    private final int FIRE_DELAY;
    protected int fireCounter;
    private boolean isFiring;

    BufferedImage up1, down1, left1, right1;
    BufferedImage fire_up1, fire_down1, fire_left1, fire_right1;


    public Gunner(FacingDirection direction) {
        this.type = "Gunner";
        this.facingDirection = direction;
        this.FIRE_DELAY = 18;
        this.isFiring = false;
        this.setContactDamageAmount(50);

        fireCounter = -30;
    }

    public void getEntityImage() {

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/gunner_up.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/gunner_down.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/gunner_left.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/gunner_right.png"));

            fire_up1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/gunner_up_fire.png"));
            fire_down1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/gunner_down_fire.png"));
            fire_left1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/gunner_left_fire.png"));
            fire_right1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/gunner_right_fire.png"));

            image = down1;

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    protected void getFrameImage(){

        if (this.isFiring) {
            switch (this.facingDirection) {
                case UP:
                    image = fire_up1;
                    break;
                case DOWN:
                    image = fire_down1;
                    break;
                case LEFT:
                    image = fire_left1;
                    break;
                case RIGHT:
                    image = fire_right1;
                    break;
            }
        }
        else {
            switch (this.facingDirection) {
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

    public void fireProjectile(GamePanel gp){

        // temp X and Y
        int projX = this.positionX;
        int projY = this.positionY;

        Projectile proj = new Projectile(this.facingDirection);

        switch(facingDirection) {
            case UP:
                projY -= 1;
                break;
            case DOWN:
                projY += 1;
                break;
            case LEFT:
                projX -= 1;
                break;
            case RIGHT:
                projX += 1;
                break;
        }

        proj.setPositionX(projX);
        proj.setPositionY(projY);

        // request gamepanel to add the projectile entity to the map
        gp.entityAddArray.add(proj);

    }

    public void update() {

        GamePanel gp = GamePanel.getGamePanel();

        if (fireCounter % FIRE_DELAY == 0){
            fireProjectile(gp); // fires projectile
            this.isFiring = true;
        }
        else {
            this.isFiring = false;
        }

        fireCounter++;

    }
}
