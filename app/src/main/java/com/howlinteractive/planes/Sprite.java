package com.howlinteractive.planes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

    boolean stopOnAnimationFinished;
    boolean animationFinished = false;

    Sprite(int[] files, boolean cycling, boolean stopOnAnimationFinish, int depth) {
        this.cycling = cycling;
        this.stopOnAnimationFinished = stopOnAnimationFinish;
        this.depth = depth;
        textures = new ArrayList<>();
        for(int file : files) {
            textures.add(loadTexture(file));
        }
        setTexture(0);
    }

    Sprite(int file, int depth) {
        this(new int[] { file }, false, false, depth);
    }

    Sprite(int file) {
        this(new int[] { file }, false, false, 0);
    }

    void draw(RectF rect, Canvas canvas, boolean fixed, boolean rotateFromPoint, float rotateX, float rotateY) {
        if(!stopOnAnimationFinished || !animationFinished) {
            canvas.save();
            canvas.rotate((float) Math.toDegrees(Math.PI - rotation), fixed ? rect.centerX() : rotateFromPoint ? rotateX - Room.camera.getX() + Game.width / 2 : Room.camera.getRect(rect).centerX(), fixed ? rect.centerY() : rotateFromPoint ? rotateY - Room.camera.getY() + Game.height / 2 : Room.camera.getRect(rect).centerY());
            canvas.drawBitmap(texture, null, fixed ? rect : Room.camera.getRect(rect), null);
            canvas.restore();
            if (cycling) {
                if(stopOnAnimationFinished && curTexture == textures.size() - 1) {
                    animationFinished = true;
                }
                else {
                    setTexture((curTexture + 1) % textures.size());
                }
            }
        }
    }

    void draw(RectF rect, Canvas canvas, float rotateX, float rotateY) {
        draw(rect, canvas, false, true, rotateX, rotateY);
    }

    void draw(RectF rect, Canvas canvas, boolean fixed) {
        draw(rect, canvas, fixed, false, 0, 0);
    }

    void draw(RectF rect, Canvas canvas) {
        draw(rect, canvas, false, false, 0, 0);
    }

    static Bitmap loadTexture(int file, Activity activity) {
        Bitmap loaded = loadedTextures.get(file);
        if(loaded == null) {
            Bitmap t = activity == null ? Game.loadBitmap(file) : BitmapFactory.decodeResource(activity.getResources(), file);
            loadedTextures.put(file, t);
            return t;
        }
        return loaded;
    }

    static Bitmap loadTexture(int file) {
        return loadTexture(file, null);
    }
}
