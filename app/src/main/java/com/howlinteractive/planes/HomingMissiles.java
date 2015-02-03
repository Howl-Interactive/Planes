package com.howlinteractive.planes;

public class HomingMissiles extends Special {

    private static final int FIRE_RATE = 50;

    HomingMissiles() {
        super(FIRE_RATE);
    }

    @Override
    void activate(float x, float y, float angle) {
        if(cooldown != 0) { cooldown--; }
        else {
            Game.room.objs.add(new HomingMissile(x, y, angle));
            cooldown = fireRate;
        }
    }
}
