package com.howlinteractive.planes;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

public class InputPanel {

    enum INPUT_TYPE { MOVE, SHOOT }

    private static float lx = 200, ly = Game.height - 200, rx = Game.width - 200, ry = Game.height - 200;
    private static int ls = 200, rs = 200, range = 350;

    private static ControlStick leftStick, rightStick;

    static void create(INPUT_TYPE lInputType, INPUT_TYPE rInputType, int lStick, int rStick) {
        leftStick = new ControlStick(lx, ly, ls, range, lInputType, lStick);
        rightStick = new ControlStick(rx, ry, rs, range, rInputType, rStick);
    }

    static void create() {
        create(INPUT_TYPE.MOVE, INPUT_TYPE.SHOOT, R.drawable.controlstick, R.drawable.controlstick);
    }

    static void draw(Canvas canvas) {
        leftStick.draw(canvas);
        rightStick.draw(canvas);
    }

    static void onTouch(float[] coords) {
        leftStick.onTouch(coords);
        rightStick.onTouch(coords);
    }

    private static class ControlStick extends Object {

        @Override
        Type type() { return Type.NONE; }

        INPUT_TYPE inputType;

        int range;

        ControlStick(float x, float y, int s, int range, INPUT_TYPE inputType, int texture) {
            super(x, y, s, s, new Sprite(texture));
            this.range = range;
            this.inputType = inputType;
        }

        void draw(Canvas canvas) {
            sprite.draw(getRect(), canvas, true);
        }

        void onTouch(float[] coords) {
            if(Math.sqrt(Math.pow(coords[0] - x, 2) + Math.pow(coords[1] - y, 2)) < range) {
                float angle = (float)Math.atan2(coords[1] - y, coords[0] - x);
                switch(inputType) {
                    case MOVE:
                        Room.p.setVel(angle, true);
                        break;
                    case SHOOT:
                        Room.p.shoot(angle);
                        break;
                }
            }
        }
    }
}