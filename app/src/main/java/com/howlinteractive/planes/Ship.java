package com.howlinteractive.planes;

/**
 * Created by jacobmacdonald on 1/22/15.
 */
public class Ship extends Object {

    @Override
    Type type() { return Type.SHIP; }

    Ship(float x, float y) {
        super(x, y, new Sprite(R.drawable.blrect));
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
