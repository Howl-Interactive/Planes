package com.howlinteractive.planes;

import android.util.Log;

public abstract class Projectile extends Object {

    @Override
    Type type() { return Type.FRIENDLY; }

    static int SPEED = 100;

    Projectile(float x, float y, float angle, float speed) {
        super(x, y, speed, new Sprite(R.drawable.bullet));
        setVel(angle, true);
        targetDir = angle;
    }

    Projectile(float x, float y, float angle) {
        this(x, y, angle, SPEED);
    }

    @Override
    void collision(Object obj) {
        super.collision(obj);
        switch(obj.type()) {
            case ENEMY:
                takeDamage();
                obj.takeDamage();
                break;
            default:
                break;
        }
    }
}