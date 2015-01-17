package com.howlinteractive.planes;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

public class InputPanel {

    enum INPUT_TYPE { MOVE, SHOOT }

    private static float lx = 50, ly = 50, rx = Game.width - 50, ry = 50, ls = 50, rs = 50;

    private static ControlStick leftStick, rightStick;

    static void create(INPUT_TYPE lInputType, INPUT_TYPE rInputType, String lStick, String rStick) {
        leftStick = new ControlStick(lx, ly, ls, lInputType, lStick);
        rightStick = new ControlStick(rx, ry, rs, rInputType, rStick);
    }

    static void create() {
        create(INPUT_TYPE.MOVE, INPUT_TYPE.SHOOT, "controlstick.png", "controlstick.png");
    }

    static void draw(Canvas canvas) {
        leftStick.draw(canvas);
        rightStick.draw(canvas);
    }

    static void onTouch(MotionEvent event) {
        leftStick.onTouch(event);
        rightStick.onTouch(event);
    }

    private static class ControlStick {

        INPUT_TYPE inputType;

        float x, y, s;
        private RectF rect;
        RectF getRect() {
            if(rect == null) {
                rect = new RectF(x - s / 2, y - s / 2, x + s / 2, y + s / 2);
            }
            rect.left = x - s / 2;
            rect.right = x + s / 2;
            rect.top = y - s / 2;
            rect.bottom = y + s / 2;
            return rect;
        }
        Sprite sprite;

        ControlStick(float x, float y, float s, INPUT_TYPE inputType, String texture) {
            this.x = x;
            this.y = y;
            this.s = s;
            this.inputType = inputType;
            this.sprite = new Sprite(texture);
        }

        void draw(Canvas canvas) {
            sprite.draw(getRect(), canvas);
        }

        void onTouch(MotionEvent event) {
            if(Math.sqrt(Math.pow(event.getX() - x, 2) + Math.pow(event.getY() - y, 2)) < s / 2) {
                float angle = (float)Math.atan2(event.getY() - y, event.getX() - x);
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