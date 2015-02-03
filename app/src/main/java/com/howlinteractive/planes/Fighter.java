package com.howlinteractive.planes;

public class Fighter extends Enemy {

    Fighter(float x, float y) {
        super(x, y, new Sprite(new int[] { R.drawable.plane01_s, R.drawable.plane01_l, R.drawable.plane01_r }, false, false, 1));
        targetDir = getDir(Room.p);
        setDir(targetDir, true);
    }

    @Override
    void update() {
        targetDir = (float)Math.atan2(Room.p.y - y, Room.p.x - x);
        super.update();
    }
}
