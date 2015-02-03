package com.howlinteractive.planes;

public abstract class Special {

    static final int HOMING_MISSILES = 0;
    static final int SHIELD = 1;
    static final int COLLISION = 2;

    protected int cooldown = 0;
    protected int fireRate;

    int radius = 100;

    Special(int fireRate) {
        this.fireRate = fireRate;
    }

    static Special create(int type) {
        switch(type) {
            case HOMING_MISSILES:
                return new HomingMissiles();
            case SHIELD:
                return new ShieldGenerator();
            case COLLISION:
                return new CollisionBarrier();
            default:
                return null;
        }
    }

    abstract void activate(float x, float y, float angle);
}
