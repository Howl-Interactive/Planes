package com.howlinteractive.planes;

public class Shield extends Object {

    @Override
    Type type() { return Type.FRIENDLY; }

    Shield(float x, float y, int r) {
        super(x, y, r * 2, r * 2, new Sprite(R.drawable.brect));
        collisionType = CollisionType.CIRCLE;
    }

    @Override
    void update() {
        x = Room.p.x;
        y = Room.p.y;
    }

    @Override
    void takeDamage() {
        super.takeDamage();
        ((ShieldGenerator)Room.p.special).resumeCooldown();
    }
}
