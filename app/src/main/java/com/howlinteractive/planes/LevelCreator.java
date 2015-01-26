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
    static final int FIGHTER = 3;
    static final int BOMBER = 4;

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
        "340040043" +
        "000000000" +
        "000000000" +
        "000000000" +
        "000000000" +
        "000000000" +
        "000000000" +
        "300000003"
    };

    static int scrollCounter = HEIGHT;

    static ArrayList<Object> loadSection(boolean fromBottom) {
        ArrayList<Object> objs = new ArrayList<>();
        String section = sections[Game.rand.nextInt(sections.length)];
        if(fromBottom) {
            section = new StringBuilder(section).reverse().toString();
        }
        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLS; col++) {
                switch(Integer.parseInt("" + section.charAt(row * COLS + col))) {
                    case WALL:
                        objs.add(new Wall(col * TILE_SIZE + TILE_SIZE / 2, row * TILE_SIZE + (fromBottom ? Game.height : -HEIGHT) + Room.camera.getY() - Game.height / 2 + TILE_SIZE / 2, TILE_SIZE, TILE_SIZE));
                        break;
                    case FIGHTER:
                        objs.add(new Fighter(col * TILE_SIZE + TILE_SIZE / 2, row * TILE_SIZE + (fromBottom ? Game.height : -HEIGHT) + Room.camera.getY() - Game.height / 2 + TILE_SIZE / 2));
                        break;
                    case BOMBER:
                        objs.add(new Bomber(col * TILE_SIZE + TILE_SIZE / 2, row * TILE_SIZE + (fromBottom ? Game.height : -HEIGHT) + Room.camera.getY() - Game.height / 2 + TILE_SIZE / 2, fromBottom));
                        break;
                }
            }
        }
        return objs;
    }
}