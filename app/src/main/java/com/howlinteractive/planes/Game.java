package com.howlinteractive.planes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

@SuppressLint("WrongCall")
public class Game extends SurfaceView {

    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;

    public Game(Context context) {
        super(context);
    }

    public Game(Context context, final Activity main) {
        super(context);

        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                main.finish();
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

        //Level.create();
        initialize();
    }

    static Random rand = new Random();

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

    static boolean inputPanelEnabled = true;

    static int width, height;

    void initialize() {
        rooms = new ArrayList<>();
        rooms.add(new Room(0, -2));
        changeRoom(0);
        InputPanel.create();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        room.draw(canvas);
        if(inputPanelEnabled) { InputPanel.draw(canvas); }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!inputPanelEnabled) { room.onTouch(event); }
        else { InputPanel.onTouch(event); }
        return true;
    }

    static void gameOver() {
        room.reset();
    }
}