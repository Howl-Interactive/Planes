package com.howlinteractive.planes;

public abstract class Enemy extends Object {

    @Override
    Type type() { return Type.ENEMY; }

    static final float SPEED = 5, TURN_SPEED = .03f;

    Enemy(float x, float y, Sprite sprite) {
        super(x, y, SPEED, sprite);
        velY = speed;
        turnSpeed = TURN_SPEED;
    }

    @Override
    void takeDamage() {
        super.takeDamage();
        if(Game.rand.nextInt(4) == 0) {
            Game.room.objs.add(new Upgrade(x, y, Game.rand.nextInt(5)));
        }
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
            case FRIENDLY_INDESTRUCTIBLE:
                if(obj.isAlive) {
                    takeDamage();
                }
                break;
            case PLAYER:
                if(isAlive) {
                    takeDamage();
                }
                if(!(Room.p.special instanceof CollisionBarrier)) {
                    Room.p.takeDamage();
                }
                break;
            default:
                break;
        }
    }
}