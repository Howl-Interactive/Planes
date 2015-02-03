package com.howlinteractive.planes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class ProgressBar {

    RectF bar, progress;
    static Paint whitePaint, blackPaint;

    ProgressBar(float x1, float y1, float x2, float y2, String key) {
        bar = new RectF(x1, y1, x2, y2);
        progress = new RectF(100, 400, x1 + (x2 - x1) * Achievements.getPercent(key), 500);
    }

    void draw(Canvas canvas) {
        canvas.drawRect(bar, whitePaint);
        canvas.drawRect(progress, blackPaint);
    }

    static void setPaint() {
        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
    }
}
