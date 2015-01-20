package com.howlinteractive.planes;

public class Bullet extends Object {
    @Override
    Type type() { return Type.FRIENDLY; }

    Bullet(float x, float y, float angle) {
        super(x, y, new Sprite(R.drawable.bullet));
        setVel(angle, true);
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