package com.howlinteractive.planes;

import java.util.ArrayList;

public class LevelCreator {
    static final int ROWS = 8;
    static final int COLS = 8;
    static final int TILE_SIZE = Game.width / COLS;
    static final int HEIGHT = ROWS * TILE_SIZE;

    static final int EMPTY = 0;
    static final int PATH = 1;
    static final int WALL = 2;
    static final int ENEMY = 3;

    static final String EMPTY_SECTION = "0000000000000000000000000000000000000000000000000000000000000000";

    static String[] sections = {
            "0000000000000000000000000030000000000000000000000000000000000000"
    };

    static String[] backgroundFiles = {
            "background.png",
            "background2.png",
            "background3.png"
    };

    static int scrollCounter = HEIGHT;

    static ArrayList<Object> createSection(String backgroundFile, String section, boolean forceCreate, int forcedOffset) {
        ArrayList<Object> objs = new ArrayList<>();
        if(scrollCounter >= HEIGHT || forceCreate) {
            int offset = forceCreate ? forcedOffset : scrollCounter - HEIGHT;
            objs.add(new Background(offset, backgroundFile));
            for(int row = 0; row < ROWS; row++) {
                for(int col = 0; col < COLS; col++) {
                    switch(Integer.parseInt("" + section.charAt(row * ROWS + col))) {
                        case EMPTY:
                            break;
                        case PATH:
                            break;
                        case WALL:
                            objs.add(new Wall(col * TILE_SIZE + TILE_SIZE / 2, (ROWS - 1 - row) * TILE_SIZE + Game.height - offset + TILE_SIZE / 2, TILE_SIZE, TILE_SIZE));
                            break;
                        case ENEMY:
                            objs.add(new Enemy(col * TILE_SIZE + TILE_SIZE / 2, (ROWS - 1 - row) * TILE_SIZE + Game.height - offset + TILE_SIZE / 2));
                            break;
                    }
                }
            }
            if(!forceCreate) { scrollCounter = offset; }
        }
        return objs;
    }

    static ArrayList<Object> createSection(boolean randomBackground, String section, boolean forceCreate, int forcedOffset) {
        return createSection(backgroundFiles[randomBackground ? Game.rand.nextInt(backgroundFiles.length) : 0], section, forceCreate, forcedOffset);
    }

    static ArrayList<Object> createSection(String section) {
        return createSection(backgroundFiles[0], section, false, 0);
    }

    static ArrayList<Object> createSection(boolean randomBackground) {
        return createSection(backgroundFiles[randomBackground ? Game.rand.nextInt(backgroundFiles.length) : 0], sections[Game.rand.nextInt(sections.length)], false, 0);
    }

    static ArrayList<Object> createSection() {
        return createSection(backgroundFiles[0], sections[Game.rand.nextInt(sections.length)], false, 0);
    }

    static ArrayList<Object> initializeEmptyRoom() {
        scrollCounter = HEIGHT;
        ArrayList<Object> objs = new ArrayList<>();
        ArrayList<Object> temp;
        int numRooms = (int)Math.ceil(Game.height / (float)HEIGHT);
        for(int i = 0; i < numRooms; i++) {
            temp = createSection(true, EMPTY_SECTION, true, (i + 1) * HEIGHT);
            for(Object obj : temp) {
                objs.add(obj);
            }
            temp.clear();
        }
        return objs;
    }

    private static class Background extends Object {

        @Override
        Type type() { return Type.NONE; }

        Background(float offset, String file) {
            super(Game.width / 2, Game.height - offset + LevelCreator.HEIGHT / 2, Game.width, LevelCreator.HEIGHT, new Sprite(file, -1));
        }

        @Override
        void collision(Object obj) { }
    }
}