package com.howlinteractive.planes;

public class MainMenu extends Menu {

    private static Button[] BUTTONS = new Button[] {
        new Button(100, 100, 150, 150, R.drawable.grect, Button.Effect.GOTO_GAME),
        new Button(300, 100, 150, 150, R.drawable.rrect, Button.Effect.GOTO_ACHIEVEMENTS),
        new Button(100, 700, 150, 150, R.drawable.wrect, Button.Effect.SELECT_MACHINE_GUN),
        new Button(300, 700, 150, 150, R.drawable.wrect, Button.Effect.SELECT_MISSILE_LAUNCHER),
        new Button(500, 700, 150, 150, R.drawable.wrect, Button.Effect.SELECT_SHOTGUN),
        new Button(700, 700, 150, 150, R.drawable.wrect, Button.Effect.SELECT_LASER),
        new Button(100, 1000, 150, 150, R.drawable.wrect, Button.Effect.SELECT_HOMING_MISSILES),
        new Button(300, 1000, 150, 150, R.drawable.wrect, Button.Effect.SELECT_SHIELD),
        new Button(500, 1000, 150, 150, R.drawable.wrect, Button.Effect.SELECT_COLLISION)
    };

    private static Label[] LABELS = new Label[] {
        new Label("WEAPONS", 300, 600),
        new Label("SPECIALS", 300, 900),
    };

    MainMenu() {
        super(BUTTONS, LABELS);
    }
}
