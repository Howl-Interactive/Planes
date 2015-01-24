package com.howlinteractive.planes;

/**
 * Created by jacobmacdonald on 1/23/15.
 */
public class Fighter extends Enemy {

    Fighter(float x, float y) {
        super(x, y);
        targetDir = (float)Math.atan2(Room.p.y - y, Room.p.x - x);
        setDir(targetDir, true);
    }

    @Override
    void update() {
        targetDir = (float)Math.atan2(Room.p.y - y, Room.p.x - x);
        super.update();
    }
}
