package com.howlinteractive.planes;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by jacobmacdonald on 1/26/15.
 */
public class Laser extends Projectile {

    @Override
    Type type() { return Type.FRIENDLY_INDESTRUCTIBLE; }

    @Override
    RectF getRect() {
        if(rect == null) {
            rect = new RectF(x, y, x + length, y + width);
        }
        rect.left = x;
        rect.right = x + length;
        rect.top = y;
        rect.bottom = y + width;
        return rect;
    }

    static int length = Game.height, width = 5;

    int counter = 0, death = 3;

    Laser(float x, float y, float angle) {
        super(x, y, angle, 0);
        collisionType = CollisionType.LINE;
        x2 = x + (float)(length * Math.cos(angle) - width * Math.sin(angle));
        y2 = y + (float)(length * Math.sin(angle) + width * Math.cos(angle));
        setRotation((float)Math.PI - angle);
    }

    @Override
    void update() {
        handleCollisions();
        counter++;
        if(counter >= death) {
            isAlive = false;
        }
    }

    @Override
    void draw(Canvas canvas) {
        sprite.draw(getRect(), canvas, x, y);
    }

    @Override
    void collision(Object obj) {
        switch(obj.type()) {
            case ENEMY:
                obj.takeDamage();
                break;
            default:
                break;
        }
    }
}
