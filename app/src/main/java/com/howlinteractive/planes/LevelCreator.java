package com.howlinteractive.planes;

import java.util.ArrayList;

public class LevelCreator {
    static final int ROWS = 8;
    static final int COLS = 9;
    static final int TILE_SIZE = Game.width / COLS;
    static final int HEIGHT = ROWS * TILE_SIZE;

    static final int EMPTY = 0;
    static final int PATH = 1;
    static final int WALL = 2;
    static final int ENEMY = 3;

    static final String EMPTY_SECTION =
        "000000000" +
        "000000000" +
        "000000000" +
        "000000000" +
        "000000000" +
        "000000000" +
        "000000000" +
        "000000000"
    ;

    static String[] sections = {
        "330000033" +
        "000000000" +
        "000000000" +
        "000000000" +
        "000000000" +
        "000000000" +
        "000000000" +
        "300000003"
    };

    static int scrollCounter = HEIGHT;

    static ArrayList<Object> loadSection() {
        ArrayList<Object> objs = new ArrayList<>();
        String section = sections[Game.rand.nextInt(sections.length)];
        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLS; col++) {
                switch(Integer.parseInt("" + section.charAt(row * COLS + col))) {
                    case WALL:
                        objs.add(new Wall(col * TILE_SIZE + TILE_SIZE / 2, row * TILE_SIZE - HEIGHT + Game.camera.getY() - Game.height / 2 + TILE_SIZE / 2, TILE_SIZE, TILE_SIZE));
                        break;
                    case ENEMY:
                        objs.add(new Enemy(col * TILE_SIZE + TILE_SIZE / 2, row * TILE_SIZE - HEIGHT + Game.camera.getY() - Game.height / 2 + TILE_SIZE / 2));
                        break;
                }
            }
        }
        return objs;
    }
}