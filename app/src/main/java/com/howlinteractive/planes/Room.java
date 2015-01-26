package com.howlinteractive.planes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Room {

    static Camera camera;

    static float playerStartX = Game.width / 2, playerStartY = 0, shipStartX = Game.width / 2, shipStartY = playerStartY - Game.height / 4;
    static Player p;
    static Ship s;

    ArrayList<Object> objs;

    float scrollX, scrollY;

    private int waveTime = 500, waveCounter;
    boolean fromBottom;

    Paint black = new Paint();

    Room(float scrollX, float scrollY) {
        this.scrollX = scrollX;
        this.scrollY = scrollY;
        initialize();
    }

    Room() {
        this(0, 0);
    }

    void reset() {
        initialize();
    }

    void initialize() {
        objs = new ArrayList<>();
        p = new Player(playerStartX, playerStartY);
        objs.add(p);
        s = new Ship(shipStartX, shipStartY);
        objs.add(s);
        objs.add(new Background(Game.height * 3 / 2));
        objs.add(new Background(Game.height / 2));
        objs.add(new Background(-Game.height / 2));
        camera = new Camera(Room.p, false, true, Game.width / 2, 0);
        waveCounter = waveTime;
        fromBottom = false;
        black.setColor(Color.BLACK);
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
        for (int i = objs.size() - 1; i >= 0; i--) {
            if (!objs.get(i).isAlive) {
                if (objs.get(i).type() == Object.Type.ENEMY) {
                    Achievements.increment("kills");
                }
                objs.remove(i);
            }
        }
        scroll();
        if(!objs.contains(p)) {
            Game.gameOver();
        }
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
        canvas.drawRect(0, p.boundY1 - 10 - camera.getY() + Game.height / 2, Game.width, p.boundY1 - camera.getY() + Game.height / 2, black);
        canvas.drawRect(0, p.boundY2 - camera.getY() + Game.height / 2, Game.width, p.boundY2 + 10 - camera.getY() + Game.height / 2, black);
    }

    void createObjects() {
        ArrayList<Object> section = LevelCreator.loadSection(fromBottom);
        for(Object obj : section) {
            objs.add(obj);
        }
        objs.add(new WaveMarker(!fromBottom));
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