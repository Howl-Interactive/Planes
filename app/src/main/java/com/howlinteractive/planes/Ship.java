package com.howlinteractive.planes;

public class Ship extends Object {

    @Override
    Type type() { return Type.SHIP; }

    Ship(float x, float y) {
        super(x, y, new Sprite(R.drawable.carrier01));
        targetDir = (float)Math.PI / -2f;
    }

    @Override
    void collision(Object obj) {
        switch(obj.type()) {
            case BOMB:
                if(obj.isAlive) {
                    takeDamage();
                    obj.takeDamage();
                }
                break;
        }
    }
}
