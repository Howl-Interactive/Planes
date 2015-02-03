package com.howlinteractive.planes;

public class MissileLauncher extends Weapon {

    private static final int FIRE_RATE = 40;

    private boolean firstShot = true;
    private int BURST_COOLDOWN = 3;

    MissileLauncher() {
        super(FIRE_RATE);
    }

    @Override
    void shoot(float x, float y, float angle) {
        if(cooldown != 0) {
            cooldown--;
        }
        else {
            Game.room.objs.add(new Missile(x, y, angle));
            if(firstShot) {
                cooldown = BURST_COOLDOWN;
                firstShot = false;
                return;
            }
            else {
                firstShot = true;
            }
            cooldown = fireRate;
        }
    }

    @Override
    void upgrade() {
        level++;
    }
}
