package com.howlinteractive.planes;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class InputPanel {

    enum INPUT_TYPE { MOVE, SHOOT }

    private static float lx = Game.width / 2, ly = Game.height - 200;
    private static int ls = 300, range = 450;

    private static ControlStick leftStick, rightStick;

    static void create(INPUT_TYPE lInputType, int lStick) {
        leftStick = new ControlStick(lx, ly, ls, range, lInputType, lStick);
    }

    static void create() {
        create(INPUT_TYPE.MOVE, R.drawable.controlstick);
    }

    static void draw(Canvas canvas) {
        leftStick.draw(canvas);
    }

    static void onTouch(float[] coords) {
        leftStick.onTouch(coords);
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
            if(!Room.p.checkBounds()) {
                if (Math.sqrt(Math.pow(coords[0] - x, 2) + Math.pow(coords[1] - y, 2)) < range) {
                    float angle = (float) Math.atan2(coords[1] - y, coords[0] - x);
                    switch (inputType) {
                        case MOVE:
                            Room.p.targetDir = angle;
                            break;
                        case SHOOT:
                            Room.p.shoot(angle);
                            break;
                    }
                }
            }
        }
    }
}