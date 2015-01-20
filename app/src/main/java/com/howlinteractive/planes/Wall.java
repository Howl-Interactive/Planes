package com.howlinteractive.planes;

public class Wall extends Object {

    @Override
    Type type() { return Type.SOLID; }

    Wall(float x, float y, int w, int h) {
        super(x, y, w, h, new Sprite(R.drawable.wall));
    }
}