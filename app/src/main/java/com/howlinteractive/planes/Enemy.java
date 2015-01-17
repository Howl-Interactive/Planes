package com.howlinteractive.planes;

public class Enemy extends Object {

    @Override
    Type type() { return Type.ENEMY; }

    static final float SPEED = 3;

    Enemy(float x, float y) {
        super(x, y, SPEED, new Sprite("enemy.png"));
    }

    @Override
    void update() {
        setVel((float)Math.atan2(Room.p.y - y, Room.p.x - x), true);
        super.update();
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