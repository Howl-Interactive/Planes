package com.howlinteractive.planes;

public class GameState {

    static final int MAIN_MENU = 0, ACHIEVEMENTS_MENU = 1, GAME = 2;
    private static int state = MAIN_MENU;

    static void changeState(int state) {
        switch(GameState.state) {
            case GAME:
                Achievements.save();
                break;
        }
        switch(state) {
            case GAME:
                Game.gameOver();
                break;
            case MAIN_MENU:
                Game.mainMenu.reload();
                break;
            case ACHIEVEMENTS_MENU:
                Game.achievementsMenu.reload();
                break;
        }
        GameState.state = state;
        Game.clearTouchEvents();
    }

    static int getState() {
        return state;
    }
}
