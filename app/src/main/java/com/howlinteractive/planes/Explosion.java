package com.howlinteractive.planes;

/**
 * Created by jacobmacdonald on 1/26/15.
 */
public class Explosion extends Object {

    @Override
    Type type() { return Type.FRIENDLY_INDESTRUCTIBLE; }

    int counter = 0, death = 50;

    Explosion(float x, float y) {
        super(x, y, new Sprite(R.drawable.grect));
    }

    @Override
    void update() {
        super.update();
        counter++;
        if(counter >= death) {
            isAlive = false;
        }
    }

    @Override
    void collision(Object obj) {
        switch(obj.type()) {
            case ENEMY:
                takeDamage();
                obj.takeDamage();
                break;
        }
    }
}
