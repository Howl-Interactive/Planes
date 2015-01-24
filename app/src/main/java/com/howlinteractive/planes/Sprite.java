package com.howlinteractive.planes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Hashtable;

public class Sprite {

    private static Hashtable<Integer, Bitmap> loadedTextures = new Hashtable<>();

    private ArrayList<Bitmap> textures;
    private int curTexture;
    private Bitmap texture;
    public void setTexture(int newTexture) { curTexture = newTexture; texture = textures.get(curTexture); }
    public int numTextures() { return textures.size(); }

    int width() { return texture.getWidth(); }
    int height() { return texture.getHeight(); }

    private boolean cycling;
    void toggleAnimation() { cycling = !cycling; }

    float rotation = 0;

    float depth;

    Sprite(int[] files, boolean cycling, int depth) {
        this.cycling = cycling;
        this.depth = depth;
        textures = new ArrayList<>();
        for(int file : files) {
            textures.add(loadTexture(file));
        }
        setTexture(0);
    }

    Sprite(int file, int depth) {
        this(new int[] { file }, false, depth);
    }

    Sprite(int file) {
        this(new int[] { file }, false, 0);
    }

    void draw(RectF rect, Canvas canvas, boolean fixed) {
        canvas.save();
        canvas.rotate((float)Math.toDegrees(Math.PI - rotation), fixed ? rect.centerX() : Game.camera.getRect(rect).centerX(), fixed ? rect.centerY() : Game.camera.getRect(rect).centerY());
        canvas.drawBitmap(texture, null, fixed ? rect : Game.camera.getRect(rect), null);
        canvas.restore();
        if(cycling) { setTexture((curTexture + 1) % textures.size()); }
    }

    void draw(RectF rect, Canvas canvas) {
        draw(rect, canvas, false);
    }

    static Bitmap loadTexture(int file) {
        Bitmap loaded = loadedTextures.get(file);
        if(loaded == null) {
            Bitmap t = Game.loadBitmap(file);
            loadedTextures.put(file, t);
            return t;
        }
        return loaded;
    }
}
