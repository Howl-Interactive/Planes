package com.howlinteractive.planes;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends ActionBarActivity {

    private static Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        display = getWindowManager().getDefaultDisplay();
        Game.width = display.getWidth();
        Game.height = display.getHeight();
        setContentView(R.layout.activity_main);
        Achievements.load(PreferenceManager.getDefaultSharedPreferences(this));
    }

    public void changeActivity(View view) {
        Intent intent;
        switch(view.getId()) {
            case R.id.start_game:
                intent = new Intent(this, GameActivity.class);
                break;
            case R.id.view_achievements:
                intent = new Intent(this, AchievementsActivity.class);
                break;
            default:
                intent = null;
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }
}
