package com.howlinteractive.planes;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import java.util.HashMap;

public class AchievementsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        HashMap<String, Integer> achievements = Achievements.get();
        TextView tv;
        for(HashMap.Entry<String, Integer> entry : achievements.entrySet()){
            tv = new TextView(this);
            tv.setText(entry.getKey() + ": " + entry.getValue());
            layout.addView(tv);
        }

        setContentView(layout, layoutParams);

/*
        setContentView(R.layout.activity_achievements);
        */
    }

    public void backToMain(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
