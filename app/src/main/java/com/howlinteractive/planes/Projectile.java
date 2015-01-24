package com.howlinteractive.planes;

import android.util.Log;

public abstract class Projectile extends Object {
    @Override
    Type type() { return Type.FRIENDLY; }

    int SPEED = 100;

    Projectile(float x, float y, float angle) {
        super(x, y, new Sprite(R.drawable.bullet));
        speed = SPEED;
        setVel(angle, true);
        targetDir = angle;
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