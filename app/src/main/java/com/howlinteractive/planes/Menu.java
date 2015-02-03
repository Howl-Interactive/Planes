package com.howlinteractive.planes;

import android.graphics.Canvas;

public class Menu {

    Button[] buttons;
    Label[] labels;

    Menu(Button[] buttons, Label[] labels) {
        this.buttons = buttons;
        this.labels = labels;
    }

    void draw(Canvas canvas) {
        for(Button button : buttons) {
            button.draw(canvas);
        }
        for(Label label : labels) {
            label.draw(canvas);
        }
    }

    void update() { }

    void onTouch(float[] coords) {
        for(Button button : buttons) {
            button.onTouch(coords);
        }
    }

    void reload() { }
}
