package com.howlinteractive.planes;

public abstract class Weapon {

    private static final int MACHINE_GUN = 0;
    private static final int MISSILE_LAUNCHER = 1;
    private static final int SHOTGUN = 2;
    private static final int LASER = 3;

    protected int cooldown = 0;
    protected int fireRate;
    protected int level;

    Weapon(int fireRate) {
        this.fireRate = fireRate;
    }

    static Weapon create(int type) {
        switch(type) {
            case MACHINE_GUN:
                return new MachineGun();
            case MISSILE_LAUNCHER:
                return new MissileLauncher();
            case SHOTGUN:
                return new Shotgun();
            case LASER:
                return new LaserGun();
            default:
                return null;
        }
    }

    abstract void shoot(float x, float y, float angle);

    abstract void upgrade();
}
