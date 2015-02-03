package com.howlinteractive.planes;

import android.graphics.Canvas;

public class Button extends Object {

    @Override
    Type type() { return Type.NONE; }

    enum Effect {
        GOTO_MAIN_MENU, GOTO_ACHIEVEMENTS, GOTO_GAME,
        SELECT_MACHINE_GUN, SELECT_MISSILE_LAUNCHER, SELECT_SHOTGUN, SELECT_LASER,
        SELECT_HOMING_MISSILES, SELECT_SHIELD, SELECT_COLLISION
    }
    Effect effect;

    Button(float x, float y, int w, int h, int image, Effect effect) {
        super(x, y, w, h, new Sprite(image));
        this.effect = effect;
    }

    @Override
    void draw(Canvas canvas) {
        sprite.draw(getRect(), canvas, true);
    }

    void onTouch(float[] coords) {
        if(collisionPoint(coords[0], coords[1], this)) {
            activate();
        }
    }

    void activate() {
        switch(effect) {
            case GOTO_MAIN_MENU:
                GameState.changeState(GameState.MAIN_MENU);
                break;
            case GOTO_ACHIEVEMENTS:
                GameState.changeState(GameState.ACHIEVEMENTS_MENU);
                break;
            case GOTO_GAME:
                GameState.changeState(GameState.GAME);
                break;
            case SELECT_MACHINE_GUN:
                Game.save("weapon", 0);
                Room.p.loadWeapon();
                break;
            case SELECT_MISSILE_LAUNCHER:
                Game.save("weapon", 1);
                Room.p.loadWeapon();
                break;
            case SELECT_SHOTGUN:
                Game.save("weapon", 2);
                Room.p.loadWeapon();
                break;
            case SELECT_LASER:
                Game.save("weapon", 3);
                Room.p.loadWeapon();
                break;
            case SELECT_HOMING_MISSILES:
                Game.save("special", 0);
                Room.p.loadSpecial();
                break;
            case SELECT_SHIELD:
                Game.save("special", 1);
                Room.p.loadSpecial();
                break;
            case SELECT_COLLISION:
                Game.save("special", 2);
                Room.p.loadSpecial();
                break;
        }
    }
}