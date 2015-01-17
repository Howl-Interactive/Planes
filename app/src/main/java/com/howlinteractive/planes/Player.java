package com.howlinteractive.planes;

public class Player extends Object {

    @Override
    Type type() { return Type.PLAYER; }

    Player(float x, float y) {
        super(x, y, new Sprite("player.png"));
    }

    @Override
    void update() {
        if(cooldown != 0) { cooldown--; }
        super.update();
        velX = 0;
        velY = 0;
    }

    int cooldown = 0;
    final int FIRE_RATE = 20;
    void shoot(float angle) {
        if(cooldown == 0) {
            Game.room.objs.add(new Bullet(x, y, angle));
            cooldown = FIRE_RATE;
        }
    }

    @Override
    void takeDamage() {
        Game.gameOver();
    }

    @Override
    void collision(Object obj) {
        super.collision(obj);
        switch(obj.type()) {
            case ENEMY:
                Game.gameOver();
                break;
            default:
                break;
        }
    }
}