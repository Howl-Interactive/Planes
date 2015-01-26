package com.howlinteractive.planes;

/**
 * Created by jacobmacdonald on 1/25/15.
 */
public class Missile extends Projectile {

    static float SPEED = 1, ACCELERATION = 1.8f;

    Missile(float x, float y, float angle) {
        super(x, y, angle, SPEED);
        accX = ACCELERATION;
    }

    @Override
    void takeDamage() {
        super.takeDamage();
        if(!isAlive) {
            Game.room.objs.add(new Explosion(x, y));
        }
    }
}
