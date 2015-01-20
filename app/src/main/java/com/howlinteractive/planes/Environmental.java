package com.howlinteractive.planes;

public class Environmental extends Object {

    @Override
    Type type() { return Type.NONE; }

    Environmental(float x, float y, int file) {
        super(x, y, new Sprite(file));
    }
}