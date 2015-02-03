package com.howlinteractive.planes;

public class ShieldGenerator extends Special {

    private static final int FIRE_RATE = 100;

    private boolean haltCooldown = false;

    ShieldGenerator() {
        super(FIRE_RATE);
    }

    @Override
    void activate(float x, float y, float angle) {
        if(cooldown != 0 && !haltCooldown) { cooldown--; }
        else {
            for(Object obj : Game.room.objs) {
                if(obj instanceof Shield) {
                    return;
                }
            }
            haltCooldown = true;
            Game.room.objs.add(new Shield(x, y, radius));
            cooldown = fireRate;
        }
    }

    void resumeCooldown() {
        haltCooldown = false;
    }
}
