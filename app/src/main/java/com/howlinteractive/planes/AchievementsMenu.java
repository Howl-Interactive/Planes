package com.howlinteractive.planes;

import android.graphics.Canvas;

public class AchievementsMenu extends Menu {

    private static Button[] BUTTONS = new Button[] {
        new Button(100, 100, 150, 150, R.drawable.wrect, Button.Effect.GOTO_MAIN_MENU)
    };

    private static Label[] getLabels() {
        return new Label[] {
            new Label("Level " + Achievements.getLevel("kills"), Game.width - 250, 575),
            new Label("Kills: " + Achievements.get("kills") + " / " + (int)Achievements.getNextTarget("kills"), 100, 375)
        };
    }

    ProgressBar[] progressBars;
    private static ProgressBar[] getProgressBars() {
        return new ProgressBar[] {
            new ProgressBar(100, 400, Game.width - 100, 500, "kills")
        };
    }

    AchievementsMenu() {
        super(BUTTONS, getLabels());
        ProgressBar.setPaint();
        progressBars = getProgressBars();
    }

    @Override
    void reload() {
        labels = getLabels();
        progressBars = getProgressBars();
    }

    @Override
    void draw(Canvas canvas) {
        for(ProgressBar bar : progressBars) {
            bar.draw(canvas);
        }
        super.draw(canvas);
    }
}
