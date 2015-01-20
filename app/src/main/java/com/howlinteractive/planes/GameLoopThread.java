package com.howlinteractive.planes;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

@SuppressLint("WrongCall")
public class GameLoopThread extends Thread {

    Game view;
    boolean running = false;

    GameLoopThread(Game view) {
        this.view = view;
    }

    @Override
    public void run() {
        while (running) {
            Canvas c = null;
            try {
                c = view.getHolder().lockCanvas();
                if(c != null) {
                    synchronized (view.getHolder()) {
                        view.onDraw(c);
                        update();
                    }
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }

        }
    }

    void update() {
        Game.update();
    }
}