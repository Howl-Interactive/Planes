package com.howlinteractive.planes;

public class Shotgun extends Weapon {

    private static final int FIRE_RATE = 40;

    private float numPellets = 5;
    private float spread = (float)Math.PI / 20f;

    Shotgun() {
        super(FIRE_RATE);
    }

    @Override
    void shoot(float x, float y, float angle) {
        if (cooldown != 0) {
            cooldown--;
        }
        else {
            for (int i = -2; i < numPellets - 2; i++) {
                Game.room.objs.add(new Pellet(x, y, angle + i * spread));
            }
            cooldown = fireRate;
        }
    }

    @Override
    void upgrade() {
        level++;
        fireRate -= 7;
    }
}
