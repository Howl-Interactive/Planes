package com.howlinteractive.planes;

public class Upgrade extends Object {

    @Override
    Type type() { return Type.UPGRADE; }

    static final int WEAPON = 0;
    static final int SPECIAL = 1;
    static final int SPEED = 2;
    static final int DAMAGE = 3;
    static final int HEALTH = 4;
    int upgradeType;

    static final int[] images = {
        R.drawable.rrect,
        R.drawable.prect,
        R.drawable.wrect,
        R.drawable.yrect,
        R.drawable.grect
    };

    Upgrade(float x, float y, int upgradeType) {
        super(x, y, new Sprite(images[upgradeType]));
        this.upgradeType = upgradeType;
    }

    @Override
    void collision(Object obj) {
        switch(obj.type()) {
            case PLAYER:
                if(isAlive) {
                    takeDamage();
                    upgradePlayer();
                }
                break;
        }
    }

    void upgradePlayer() {
        switch(upgradeType) {
            case WEAPON:
                if(Room.p.weaponL < Player.maxUpgrade) {
                    Room.p.weaponL++;
                    Room.p.upgradeWeapon();
                }
                break;
            case SPECIAL:
                if(Room.p.specialL < Player.maxUpgrade) {
                    Room.p.specialL++;
                }
                break;
            case SPEED:
                if(Room.p.speedL < Player.maxUpgrade) {
                    Room.p.speedL++;
                }
                break;
            case DAMAGE:
                if(Room.p.damageL < Player.maxUpgrade) {
                    Room.p.damageL++;
                }
                break;
            case HEALTH:
                if(Room.p.healthL < Player.maxUpgrade) {
                    Room.p.healthL++;
                }
                break;
        }
    }
}
