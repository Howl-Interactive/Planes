package com.howlinteractive.planes;

/**
 * Created by jacobmacdonald on 1/26/15.
 */
public class Pellet extends Projectile {

    static int SPEED = 40;

    Pellet(float x, float y, float angle) {
        super(x, y, angle, SPEED);
    }
}
