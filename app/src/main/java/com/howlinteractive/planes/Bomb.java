package com.howlinteractive.planes;

public class Bomb extends Projectile {

    @Override
    Type type() { return Type.BOMB; }

    Bomb(float x, float y, float angle) {
        super(x, y, angle);
    }

    @Override
    void collision(Object obj) {
        switch(obj.type()) {
            case SHIP:
                takeDamage();
                obj.takeDamage();
                break;
            default:
                break;
        }
    }
}
