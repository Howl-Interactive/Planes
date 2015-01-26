package com.howlinteractive.planes;

import android.graphics.Canvas;

/**
 * Created by jacobmacdonald on 1/24/15.
 */
public class WaveMarker extends Object {

    int counter = 0, blinkTime = 20, timeAlive = 200;

    @Override
    Type type() { return Type.NONE; }

    WaveMarker(boolean top) {
        super(Game.width / 2, top ? 50 : Game.height - 50, new Sprite(R.drawable.plane01_s));
        setRotation((float)Math.PI / 2f * (top ? 1 : -1));
    }

    @Override
    void update() {
        counter++;
        if(counter >= timeAlive) {
            isAlive = false;
        }
    }

    @Override
    void draw(Canvas canvas) {
        if((counter / blinkTime) % 2 == 0) {
            sprite.draw(getRect(), canvas, true);
        }
    }
}
