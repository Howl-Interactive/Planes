package com.howlinteractive.planes;

import android.graphics.Canvas;

public class InputPanel {

    private static float lx = Game.width / 2, ly = Game.height - 400;
    private static int ls = 300, range = 450;

    private static ControlStick stick;

    static void create() {
        stick = new ControlStick(lx, ly, ls, range);
    }

    static void draw(Canvas canvas) {
        stick.draw(canvas);
    }

    static void onTouch(float[] coords) {
        stick.onTouch(coords);
    }

    private static class ControlStick extends Object {

        @Override
        Type type() { return Type.NONE; }

        int range;

        ControlStick(float x, float y, int s, int range) {
            super(x, y, s, s, new Sprite(R.drawable.controlstick, 2));
            this.range = range;
        }

        void draw(Canvas canvas) {
            sprite.draw(getRect(), canvas, true);
        }

        void onTouch(float[] coords) {
            if(!Room.p.checkBounds()) {
                float distance = (float)Math.sqrt(Math.pow(coords[0] - x, 2) + Math.pow(coords[1] - y, 2));
                if (distance < range) {
                    Room.p.targetDir = (float) Math.atan2(coords[1] - y, coords[0] - x);;
                    if(distance > range / 2) {
                        Room.p.shoot(Room.p.getDir());
                    }
                }
            }
        }
    }
}