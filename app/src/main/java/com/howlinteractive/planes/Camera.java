package com.howlinteractive.planes;

import android.graphics.RectF;

public class Camera {

    Object obj;
    boolean bindX, bindY;
    float unboundX, unboundY;
    float getX() { return !bindX || obj == null ? unboundX : obj.x; }
    float getY() { return !bindY || obj == null ? unboundY : obj.y; }

    Camera(Object obj, boolean bindX, boolean bindY, float unboundX, float unboundY) {
        this.obj = obj;
        this.bindX = bindX;
        this.bindY = bindY;
        this.unboundX = unboundX;
        this.unboundY = unboundY;
    }

    Camera(Object obj) {
        this(obj, true, true, 0, 0);
    }

    Camera(float unboundX, float unboundY) {
        this(null, false, false, unboundX, unboundY);
    }

    Camera() {
        this(null, false, false, Game.width / 2, Game.height / 2);
    }

    RectF getRect(RectF original) {
        return new RectF(
            original.left - getX() + Game.width / 2,
            original.top - getY() + Game.height / 2,
            original.right - getX() + Game.width / 2,
            original.bottom - getY() + Game.height / 2
        );
    }
}