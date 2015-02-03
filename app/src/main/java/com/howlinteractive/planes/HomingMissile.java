package com.howlinteractive.planes;

public class HomingMissile extends Missile {

    static float SPEED = 30, ACCELERATION = .8f, TURN_SPEED = .05f, GRAVITY_TURN_FACTOR = .01f;

    int range = 700;

    int counter = 0, death = 100;

    HomingMissile(float x, float y, float angle) {
        super(x, y, angle, SPEED);
        accX = ACCELERATION;
        turnSpeed = TURN_SPEED;
    }

    @Override
    void update() {
        Object target = null;
        float minDistance = range;
        float newDistance;
        for(Object obj : Game.room.objs) {
            if(obj.type() == Type.ENEMY && (newDistance = distance(obj)) < minDistance) {
                minDistance = newDistance;
                target = obj;
            }
        }
        targetDir = getDir(target);
        turnSpeed = TURN_SPEED + (range - minDistance) / 100 * GRAVITY_TURN_FACTOR;
        super.update();
        counter++;
        if(counter >= death) {
            takeDamage();
        }
    }

    @Override
    void accelerate() {
        /*float angle = getDir(target);
        float rotatedAccX = (float)(accX * Math.cos(angle) - accY * Math.sin(angle));
        float rotatedAccY = (float)(accX * Math.sin(angle) + accY * Math.cos(angle));
        velX += rotatedAccX;
        velY += rotatedAccY;
        if(Math.abs(velX) > MAX_VEL) { velX = Math.signum(velX) * MAX_VEL; }
        if(Math.abs(velY) > MAX_VEL) { velY = Math.signum(velY) * MAX_VEL; }*/
    }
}
