package ca.sfu.group9.Entity.Character;

import ca.sfu.group9.Entity.Entity;

public class Character extends Entity {

    /**
     * Direction that a character is facing.
     */
    public enum FacingDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    protected FacingDirection facingDirection;

    public Character() {
        this.type = "Character";
    }
}
