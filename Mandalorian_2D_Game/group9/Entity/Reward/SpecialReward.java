package ca.sfu.group9.Entity.Reward;

import javax.imageio.ImageIO;
import java.io.IOException;

public class SpecialReward  extends Reward {

    private int age; // gets the game of the reward in ticks
    private int DISAPPEAR_TIME = 30; // disappears after 30 ticks

    // constuctor
    public SpecialReward() {

        this.type = "SpecialReward";
        this.setScoreValue(1000);
        this.age = 0;
    }

    // get special rewards image
    public void getEntityImage() {

        try {

            image = ImageIO.read(getClass().getResourceAsStream("/sprites/reward/beskar_1.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        this.age++; // increment age

        if (this.age >= this.DISAPPEAR_TIME) {
            this.removeOnNextTick = true;
        }
    }
}
