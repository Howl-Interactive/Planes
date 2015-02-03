package com.howlinteractive.planes;

public class Bomber extends Enemy {

    int payloads = 1;
    float range = 300;
    int cooldown = 0;
    final int FIRE_RATE = 50;

    boolean fromBottom;

    Bomber(float x, float y, boolean fromBottom) {
        super(x, y, new Sprite(new int[] { R.drawable.plane03_s, R.drawable.plane03_l, R.drawable.plane03_r }, false, false, 1));
        targetDir = (float)Math.atan2(Room.s.y - y, Room.s.x - x);
        setDir(targetDir, true);
        this.fromBottom = fromBottom;
    }

    @Override
    void update() {
        if(payloads > 0) {
            targetDir = (float)Math.atan2(Room.s.y - y, Room.s.x - x);
            if(cooldown > 0) { cooldown--; }
            else {
                if(Math.sqrt(Math.pow(y - Room.s.y, 2) + Math.pow(x - Room.s.x, 2)) < range) {
                    Game.room.objs.add(new Bomb(x, y, targetDir));
                    payloads--;
                    cooldown = FIRE_RATE;
                }
            }
        }
        else {
            targetDir = (float)Math.PI / 2f * (fromBottom ? -1 : 1);
        }
        super.update();
    }
}
