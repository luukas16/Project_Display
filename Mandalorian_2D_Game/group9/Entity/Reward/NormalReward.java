package ca.sfu.group9.Entity.Reward;


import javax.imageio.ImageIO;
import java.io.IOException;

public class NormalReward extends Reward{

    private final int normalRewardScore; // increases the players score

    // constructor
    public NormalReward() {
        normalRewardScore = 10;
        this.type = "NormalReward";
        this.setScoreValue(normalRewardScore);
    }

    // get normal rewards image
    public void getEntityImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/sprites/reward/reward_2.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // nothing to update
    }
}
