package com.howlinteractive.planes;

public class LaserGun extends Weapon {

    private static final int FIRE_RATE = 2;

    LaserGun() {
        super(FIRE_RATE);
    }

    @Override
    void shoot(float x, float y, float angle) {
        if (cooldown != 0) {
            cooldown--;
        }
        else {
            Game.room.objs.add(new Laser(x, y, angle));
            cooldown = fireRate;
        }
    }

    @Override
    void upgrade() {

    }
}
