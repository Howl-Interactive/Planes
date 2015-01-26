package com.howlinteractive.planes;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by jacobmacdonald on 1/25/15.
 */
public class Weapon {

    static final int MACHINE_GUN = 0;
    static final int MISSILE_LAUNCHER = 1;
    static final int SHOTGUN = 2;
    static final int LASER_BEAM = 3;

    int type;

    int[] FIRE_RATE = { 5, 40, 40, 2 };
    int cooldown = 0;
    int fireRate;

    boolean firstShot = true;
    int BURST_COOLDOWN = 3;

    float spread = (float)Math.PI / 20f;
    float numPellets = 5;

    Weapon(int type) {
        this.type = type;
        fireRate = FIRE_RATE[type];
    }

    void shoot(float x, float y, float angle) {
        if(cooldown != 0) { cooldown--; }
        else {
            switch (type) {
                case MACHINE_GUN:
                    Game.room.objs.add(new Bullet(x, y, angle));
                    break;
                case MISSILE_LAUNCHER:
                    Game.room.objs.add(new Missile(x, y, angle));
                    if(firstShot) {
                        cooldown = BURST_COOLDOWN;
                        firstShot = false;
                        return;
                    }
                    else {
                        firstShot = !firstShot;
                    }
                    break;
                case SHOTGUN:
                    for(int i = -2; i < numPellets - 2; i++) {
                        Game.room.objs.add(new Pellet(x, y, angle + i * spread));
                    }
                    break;
                case LASER_BEAM:
                    Game.room.objs.add(new Laser(x, y, angle));
                    break;
            }
            cooldown = fireRate;
        }
    }
}
