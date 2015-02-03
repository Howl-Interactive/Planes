package com.howlinteractive.planes;

public class Explosion extends Object {

    @Override
    Type type() { return Type.FRIENDLY_INDESTRUCTIBLE; }

    int counter = 0, death = 50;

    Explosion(float x, float y) {
        super(x, y, new Sprite(new int[] { R.drawable.ex01_0, R.drawable.ex01_1, R.drawable.ex01_2, R.drawable.ex01_3, R.drawable.ex01_4, R.drawable.ex01_5, R.drawable.ex01_6, R.drawable.ex01_7, R.drawable.ex01_8 }, true, true, 0));
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
