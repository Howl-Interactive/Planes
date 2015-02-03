package com.howlinteractive.planes;

public class Missile extends Projectile {

    static float SPEED = 1, ACCELERATION = 1.8f;

    Missile(float x, float y, float angle, float speed) {
        super(x, y, angle, speed);
        accX = ACCELERATION;
    }

    Missile(float x, float y, float angle) {
        this(x, y, angle, SPEED);
    }

    @Override
    void takeDamage() {
        super.takeDamage();
        if(!isAlive) {
            Game.room.objs.add(new Explosion(x, y));
        }
    }
}
