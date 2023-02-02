package ca.sfu.group9.Entity.Character.Enemy;

import ca.sfu.group9.Entity.Character.Character;

public class Enemy extends Character {

    private int contactDamageAmount;

    public Enemy() {
        this.type = "Enemy";
    }

    public int getContactDamageAmount() {
        return contactDamageAmount;
    }

    public void setContactDamageAmount(int contactDamageAmount) {
        this.contactDamageAmount = contactDamageAmount;
    }


}
