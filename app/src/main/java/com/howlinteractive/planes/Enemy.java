package com.howlinteractive.planes;

public abstract class Enemy extends Object {

    @Override
    Type type() { return Type.ENEMY; }

    static final float SPEED = 5, TURN_SPEED = .03f;

    Enemy(float x, float y) {
        super(x, y, SPEED, new Sprite(new int[]{ R.drawable.plane01_s, R.drawable.plane01_l, R.drawable.plane01_r }, false, 1));
        velY = speed;
        turnSpeed = TURN_SPEED;
    }

    @Override
    void collision(Object obj) {
        super.collision(obj);
        switch(obj.type()) {
            case FRIENDLY:
                if(obj.isAlive) {
                    takeDamage();
                    obj.takeDamage();
                }
                break;
            case PLAYER:
                Room.p.takeDamage();
                break;
            default:
                break;
        }
    }
}