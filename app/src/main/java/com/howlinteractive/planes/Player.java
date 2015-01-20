package com.howlinteractive.planes;

import android.util.Log;

public class Player extends Object {

    @Override
    Type type() { return Type.PLAYER; }

    Player(float x, float y) {
        super(x, y, new Sprite(R.drawable.player));
        boundX1 = 0;
        boundX2 = Game.width;
        boundY1 += Game.height / 2;
        boundY2 -= Game.height / 2;
    }

    @Override
    void update() {
        if(cooldown != 0) { cooldown--; }
        super.update();
        velX = 0;
        velY = 0;
    }

    int cooldown = 0;
    final int FIRE_RATE = 5;
    void shoot(float angle) {
        if(cooldown == 0) {
            Game.room.objs.add(new Bullet(x, y, angle));
            cooldown = FIRE_RATE;
        }
    }

    @Override
    void outOfBounds() {
        //TODO: return to battlefield
        Log.i("OOB", x < 0 ? "lx" : x > Game.width ? "rx" : "y");
    }

    @Override
    void takeDamage() {
        Game.gameOver();
    }

    @Override
    void collision(Object obj) {
        super.collision(obj);
        switch(obj.type()) {
            case ENEMY:
                Game.gameOver();
                break;
            default:
                break;
        }
    }
}