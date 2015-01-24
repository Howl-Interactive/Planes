package com.howlinteractive.planes;

/**
 * Created by jacobmacdonald on 1/23/15.
 */
public class Bomber extends Enemy {

    int payloads = 5;
    float range = 300;
    int cooldown = 0;
    final int FIRE_RATE = 50;

    Bomber(float x, float y) {
        super(x, y);
        targetDir = (float)Math.atan2(Room.s.y - y, Room.s.x - x);
        setDir(targetDir, true);
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
            targetDir = (float)Math.PI / 2f;
        }
        super.update();
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
            case PLAYER:
                Room.p.takeDamage();
                break;
            default:
                break;
        }
    }
}
