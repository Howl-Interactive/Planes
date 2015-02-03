package com.howlinteractive.planes;

import android.graphics.Canvas;

public class Player extends Object {

    @Override
    Type type() { return Type.PLAYER; }

    static float playerSpeed = 10;

    Weapon weapon;
    Special special;

    int weaponL, specialL, speedL, damageL, healthL;
    static int maxUpgrade = 5;

    Player(float x, float y) {
        super(x, y, playerSpeed, new Sprite(new int[] { R.drawable.plane02_s, R.drawable.plane02_l, R.drawable.plane02_r }, false, false, 1));
        boundX1 = 0;
        boundX2 = Game.width;
        boundY1 += Game.height / 2;
        boundY2 -= Game.height / 2;
        targetDir = -(float)Math.PI / 2f;
        velY = -speed;
        setDir(targetDir, true);
        loadWeapon();
        loadSpecial();
    }

    void loadWeapon() {
        weapon = Weapon.create(Game.loadInt("weapon", 0));
    }

    void loadSpecial() {
        special = Special.create(Game.loadInt("special", 0));
    }

    @Override
    void update() {
        super.update();
        //shoot(getDir());
        activateSpecial(getDir());
    }

    void shoot(float angle) {
        weapon.shoot(x, y, angle);
    }

    void activateSpecial(float angle) {
        special.activate(x, y, angle);
    }

    void upgradeWeapon() {
        weapon.upgrade();
    }

    @Override
    void outOfBounds() {
        if(x < boundX1) {
            targetDir = 0;
        }
        else if(x > boundX2) {
            targetDir = (float)Math.PI;
        }
        else if(y < boundY1) {
            targetDir = (float)Math.PI / 2f;
        }
        else if(y > boundY2) {
            targetDir = (float)Math.PI / -2f;
        }
    }

    @Override
    void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawText("Weapon L: " + weaponL, Game.width / 2 + 50, Game.height / 2, Game.textPaint);
        canvas.drawText("Special L: " + specialL, Game.width / 2 + 50, Game.height / 2 + 50, Game.textPaint);
        canvas.drawText("Speed L: " + speedL, Game.width / 2 + 50, Game.height / 2 + 100, Game.textPaint);
        canvas.drawText("Damage L: " + damageL, Game.width / 2 + 50, Game.height / 2 + 150, Game.textPaint);
        canvas.drawText("Health L: " + healthL, Game.width / 2 + 50, Game.height / 2 + 200, Game.textPaint);
    }

    @Override
    void collision(Object obj) {
        super.collision(obj);
        switch(obj.type()) {
            case ENEMY:
                if(obj.isAlive) {
                    obj.takeDamage();
                }
                if(!(special instanceof CollisionBarrier)) {
                    takeDamage();
                }
                break;
            case UPGRADE:
                if(obj.isAlive) {
                    ((Upgrade)obj).upgradePlayer();
                    obj.takeDamage();
                }
                break;
            default:
                break;
        }
    }
}