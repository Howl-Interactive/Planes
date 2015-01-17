package com.howlinteractive.planes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Hashtable;

public class Sprite {

    private static Hashtable<String, Bitmap> loadedTextures = new Hashtable<>();

    private ArrayList<Bitmap> textures;
    private int curTexture;
    private Bitmap texture;
    private void changeTexture(int newTexture) { curTexture = newTexture; texture = textures.get(curTexture); }

    int width() { return texture.getWidth(); }
    int height() { return texture.getHeight(); }

    private boolean cycling;
    void toggleAnimation() { cycling = !cycling; }

    float rotation = 0;

    float depth;

    Sprite(String[] files, boolean cycling, int depth) {
        this.cycling = cycling;
        this.depth = depth;
        textures = new ArrayList<>();
        for(String file : files) {
            textures.add(loadTexture(file));
        }
        changeTexture(0);
    }

    Sprite(String file, int depth) {
        this(new String[] { file }, false, depth);
    }

    Sprite(String file) {
        this(new String[]{file}, false, 0);
    }

    void draw(RectF rect, Canvas canvas) {
        canvas.drawBitmap(texture, null, rect, null);
        if(cycling) { changeTexture((curTexture + 1) % textures.size()); }
    }

    static Bitmap loadTexture(String file) {
        Bitmap loaded = loadedTextures.get(file);
        if(loaded == null) {
            Bitmap t = BitmapFactory.decodeFile(file);
            loadedTextures.put(file, t);
            return t;
        }
        return loaded;
    }
}
