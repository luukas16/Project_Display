package ca.sfu.group9.Entity.Reward;

import ca.sfu.group9.Entity.Entity;

public class Reward extends Entity {

    protected int scoreValue;

    // constructor
    public Reward() {
        this.type = "Reward";
    }

    // getter and setters
    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }
}
