package com.howlinteractive.planes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Collections;

public class Room {

    static float playerStartX = Game.width / 2, playerStartY = 0, shipStartX = Game.width / 2, shipStartY = playerStartY - Game.height / 4;
    static Player p;
    static Ship s;

    ArrayList<Object> objs;

    float scrollX, scrollY;

    private int waveTime = 50, waveCounter = waveTime;

    Paint black = new Paint();

    Room(float scrollX, float scrollY) {
        this.scrollX = scrollX;
        this.scrollY = scrollY;
        objs = new ArrayList<>();
        if(p == null) { p = new Player(playerStartX, playerStartY); }
        objs.add(p);
        s = new Ship(shipStartX, shipStartY);
        objs.add(s);
        objs.add(new Background(Game.height * 3 / 2));
        objs.add(new Background(Game.height / 2));
        objs.add(new Background(-Game.height / 2));
        black.setColor(Color.BLACK);
    }

    Room() {
        this(0, 0);
    }

    void reset() {
        objs = new ArrayList<>();
        p.x = playerStartX;
        p.y = playerStartY;
        p.setDir(0, true);
        objs.add(p);
        s = new Ship(shipStartX, shipStartY);
        objs.add(s);
        objs.add(new Background(Game.height * 3 / 2));
        objs.add(new Background(Game.height / 2));
        objs.add(new Background(-Game.height / 2));
    }

    void update() {
        if(waveCounter >= waveTime) {
            createObjects();
            waveCounter = 0;
        }
        else {
            waveCounter++;
        }
        for(int i = objs.size() - 1; i >= 0; i--) {
            if(objs.get(i).isAlive) { objs.get(i).update(); }
        }
        for(int i = objs.size() - 1; i >= 0; i--) {
            if(!objs.get(i).isAlive) { objs.remove(i); }
        }
        scroll();
    }

    void scroll() {
        p.negateScroll();
        for(Object obj : objs) {
            if(obj instanceof Projectile) {
                obj.negateScroll();
            }
        }
        for(Object obj : objs) {
            obj.x += scrollX;
            obj.y += scrollY;
        }
        LevelCreator.scrollCounter += Math.abs(scrollY);
    }

    void draw(Canvas canvas) {
        rearrangeByDepth();
        for(Object obj : objs) {
            obj.draw(canvas);
        }
        canvas.drawRect(0, p.boundY1 - 10 - Game.camera.getY() + Game.height / 2, Game.width, p.boundY1 - Game.camera.getY() + Game.height / 2, black);
        canvas.drawRect(0, p.boundY2 - Game.camera.getY() + Game.height / 2, Game.width, p.boundY2 + 10 - Game.camera.getY() + Game.height / 2, black);
    }

    boolean fromBottom = false;
    void createObjects() {
        ArrayList<Object> section = LevelCreator.loadSection(fromBottom);
        for(Object obj : section) {
            objs.add(obj);
        }
        fromBottom = !fromBottom;
    }

    void rearrangeByDepth() {
        Collections.sort(objs);
    }

    void onTouch(float[] coords) {
        if (coords[0] < p.x - p.speed) {
            p.velX = -p.speed;
        } else if (coords[0] > p.x + p.speed) {
            p.velX = p.speed;
        }
        if (coords[1] < p.y - p.speed) {
            p.velY = -p.speed;
        } else if (coords[1] > p.y + p.speed) {
            p.velY = p.speed;
        }
        p.adjustRotation();
    }

    static void drawLine(float x1, float y1, float x2, float y2) {
        //TODO
    }
}