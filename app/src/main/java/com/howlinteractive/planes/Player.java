package com.howlinteractive.planes;

public class Player extends Object {

    @Override
    Type type() { return Type.PLAYER; }

    static float playerSpeed = 10;

    Weapon weapon;

    Player(float x, float y) {
        super(x, y, playerSpeed, new Sprite(new int[]{ R.drawable.plane01_s, R.drawable.plane01_l, R.drawable.plane01_r }, false, 1));
        boundX1 = 0;
        boundX2 = Game.width;
        boundY1 += Game.height / 2;
        boundY2 -= Game.height / 2;
        targetDir = -(float)Math.PI / 2f;
        velY = -speed;
        setDir(targetDir, true);
        weapon = new Weapon(Game.loadInt("weapon", 0));
    }

    @Override
    void update() {
        super.update();
        shoot(getDir());
    }

    void shoot(float angle) {
        weapon.shoot(x, y, angle);
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
    void takeDamage() {

    }

    @Override
    void collision(Object obj) {
        super.collision(obj);
        switch(obj.type()) {
            case ENEMY:
                takeDamage();
                break;
            default:
                break;
        }
    }
}