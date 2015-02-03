package com.howlinteractive.planes;

import android.graphics.Canvas;

public class Label {

    String text;
    float x, y;

    Label(String text, float x, float y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    void draw(Canvas canvas) {
        canvas.drawText(text, x, y, Game.textPaint);
    }
}
