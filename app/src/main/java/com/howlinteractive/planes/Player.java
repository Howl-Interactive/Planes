package com.howlinteractive.planes;

public class Player extends Object {

    @Override
    Type type() { return Type.PLAYER; }

    static float playerSpeed = 10;

    Player(float x, float y) {
        super(x, y, playerSpeed, new Sprite(new int[]{ R.drawable.plane01_s, R.drawable.plane01_l, R.drawable.plane01_r }, false, 1));
        boundX1 = 0;
        boundX2 = Game.width;
        boundY1 += Game.height / 2;
        boundY2 -= Game.height / 2;
        velY = -speed;
    }

    @Override
    void update() {
        if(cooldown != 0) { cooldown--; }
        super.update();
        shoot(getDir());
    }

    int cooldown = 0;
    final int FIRE_RATE = 5;
    void shoot(float angle) {
        if(cooldown == 0) {
            Game.room.objs.add(new Bullet(x, y, angle));
            cooldown = FIRE_RATE;
        }
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
        Game.gameOver();
    }

    @Override
    void collision(Object obj) {
        super.collision(obj);
        switch(obj.type()) {
            case ENEMY:
                Game.gameOver();
                break;
            default:
                break;
        }
    }
}