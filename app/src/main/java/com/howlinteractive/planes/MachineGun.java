package com.howlinteractive.planes;

public class MachineGun extends Weapon {

    private static final int FIRE_RATE = 5;

    float spread = 25;

    MachineGun() {
        super(FIRE_RATE);
    }

    @Override
    void shoot(float x, float y, float angle) {
        if (cooldown != 0) {
            cooldown--;
        }
        else {
            Game.room.objs.add(new Bullet(x, y, angle));
            if(level > 4) {
                Game.room.objs.add(new Bullet(x + (float)(-2 * spread * Math.sin(angle)), y + (float)(2 * spread * Math.cos(angle)), angle));
                Game.room.objs.add(new Bullet(x + (float)(2 * spread * Math.sin(angle)), y + (float)(-2 * spread * Math.cos(angle)), angle));
            }
            if(level > 2) {
                Game.room.objs.add(new Bullet(x + (float)(-spread * Math.sin(angle)), y + (float)(spread * Math.cos(angle)), angle));
                Game.room.objs.add(new Bullet(x + (float)(spread * Math.sin(angle)), y + (float)(-spread * Math.cos(angle)), angle));
            }
            cooldown = fireRate;
        }
    }

    @Override
    void upgrade() {
        level++;
    }
}
