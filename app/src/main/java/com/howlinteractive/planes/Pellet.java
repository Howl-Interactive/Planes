package com.howlinteractive.planes;

public class Pellet extends Projectile {

    static int SPEED = 40;

    Pellet(float x, float y, float angle) {
        super(x, y, angle, SPEED);
    }
}