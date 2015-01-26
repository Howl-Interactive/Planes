package com.howlinteractive.planes;

import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by jacobmacdonald on 1/18/15.
 */
public class Background extends Object {

    @Override
    Type type() { return Type.NONE; }

    static int[] backgroundFiles = {
            R.drawable.background,
            R.drawable.background2,
            R.drawable.background3
    };

    Background(int y) {
        super(Game.width / 2, y, Game.width, Game.height, new Sprite(backgroundFiles, false, -1));
    }

    @Override
    void update() {
        if(y >= Game.height * 3 / 2 + Room.camera.getY() - Game.height / 2) {
            y -= Game.height * 2;
            sprite.setTexture(Game.rand.nextInt(backgroundFiles.length));
        }
        else if(y <= -Game.height / 2 + Room.camera.getY() - Game.height / 2) {
            y += Game.height * 2;
            sprite.setTexture(Game.rand.nextInt(backgroundFiles.length));
        }
    }

    @Override
    void draw(Canvas canvas) {
        sprite.draw(getRect(), canvas, Game.room.scrollX == 0 && Game.room.scrollY == 0 ? false : true);
    }

    @Override
    void outOfBounds() { }
}