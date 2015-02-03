package com.howlinteractive.planes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

@SuppressLint("WrongCall")
public class Game extends SurfaceView {

    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;

    private static Context context;
    private static MainActivity mainActivity;

    public Game(Context context) {
        super(context);
    }

    public Game(Context context, final Activity game, MainActivity mainActivity) {
        super(context);
        Game.context = context;
        Game.mainActivity = mainActivity;
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                game.finish();
                boolean retry = true;
                gameLoopThread.running = false;
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) { }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.running = true;
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }
        });

        initialize();
    }

    static Random rand = new Random();

    static int width, height;

    static boolean inputPanelEnabled = true;

    static ArrayList<Room> rooms;
    static private int curRoom;
    static Room room;
    static void nextRoom() { changeRoom(curRoom + 1); }
    static void changeRoom(int nextRoom) {
        curRoom = nextRoom;
        room = rooms.get(curRoom);
        Room.p.x = Room.playerStartX;
        Room.p.y = Room.playerStartY;
    }

    static MainMenu mainMenu;
    static AchievementsMenu achievementsMenu;

    private static ArrayList<float[]> events;

    static Paint textPaint;

    void initialize() {
        mainMenu = new MainMenu();
        achievementsMenu = new AchievementsMenu();
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50);
        rooms = new ArrayList<>();
        rooms.add(new Room(0, 0));
        changeRoom(0);
        events = new ArrayList<>();
        InputPanel.create();
    }

    static void update() {
        handleInput();
        switch(GameState.getState()) {
            case GameState.MAIN_MENU:
                mainMenu.update();
                break;
            case GameState.ACHIEVEMENTS_MENU:
                achievementsMenu.update();
                break;
            case GameState.GAME:
                room.update();
                break;
        }
    }

    static void handleInput() {
        switch(GameState.getState()) {
            case GameState.MAIN_MENU:
                for(int i = 0; i < events.size(); i++) {
                    mainMenu.onTouch(events.get(i));
                }
                break;
            case GameState.ACHIEVEMENTS_MENU:
                for(int i = 0; i < events.size(); i++) {
                    achievementsMenu.onTouch(events.get(i));
                }
                break;
            case GameState.GAME:
                for(int i = 0; i < events.size(); i++) {
                    if(!inputPanelEnabled) { room.onTouch(events.get(i)); }
                    else { InputPanel.onTouch(events.get(i)); }
                }
                break;
        }
    }

    static void clearTouchEvents() {
        events.clear();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        switch(GameState.getState()) {
            case GameState.MAIN_MENU:
                mainMenu.draw(canvas);
                break;
            case GameState.ACHIEVEMENTS_MENU:
                achievementsMenu.draw(canvas);
                break;
            case GameState.GAME:
                room.draw(canvas);
                if (inputPanelEnabled) {
                    InputPanel.draw(canvas);
                }
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        events.clear();
        if(event.getActionMasked() != MotionEvent.ACTION_UP) {
            int pointerCount = event.getPointerCount();
            for (int i = 0; i < pointerCount; i++) {
                events.add(new float[]{event.getX(i), event.getY(i)});
            }
        }
        return true;
    }

    static void gameOver() {
        room.reset();
    }

    static Bitmap loadBitmap(int file) {
        return mainActivity.loadBitmap(file);
    }

    static int loadInt(String key, int def) { return mainActivity.loadInt(key, def); }
    static int loadInt(String key) { return mainActivity.loadInt(key, -1); }
    static void save(String key, int value) { mainActivity.save(key, value); }
}