package com.howlinteractive.planes;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

public abstract class Object implements Comparable<Object> {

    enum Type { NONE, SOLID, PLAYER, ENEMY, SHIP, BOMB, FRIENDLY_INDESTRUCTIBLE, FRIENDLY }
    abstract Type type();
    enum CollisionType { RECTANGLE, LINE }
    CollisionType collisionType = CollisionType.RECTANGLE;

    Sprite sprite;
    float x, y, x2, y2, velX, velY, accX, accY;
    int w, h;
    protected RectF rect;
    RectF getRect() {
        if(rect == null) {
            rect = new RectF(x - w / 2, y - h / 2, x + w / 2, y + h / 2);
        }
        rect.left = x - w / 2;
        rect.right = x + w / 2;
        rect.top = y - h / 2;
        rect.bottom = y + h / 2;
        return rect;
    }

    final static int DEFAULT_SPEED = 15;
    float speed;

    boolean isAlive = true;

    int boundX1 = -3000, boundX2 = 3000, boundY1 = -4000, boundY2 = 4000;

    float targetDir = (float)Math.PI / 2, turnSpeed = .06f;
    final int IMAGE_RESET_FRAMES = 30;
    int imageResetCounter = IMAGE_RESET_FRAMES;

    Object(float x, float y, int w, int h, float speed, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.speed = speed;
        this.sprite = sprite;
    }

    Object(float x, float y, int w, int h, Sprite sprite) {
        this(x, y, w, h, DEFAULT_SPEED, sprite);
    }

    Object(float x, float y, float speed, Sprite sprite) {
        this(x, y, sprite.width(), sprite.height(), speed, sprite);
    }

    Object(float x, float y, Sprite sprite) {
        this(x, y, sprite.width(), sprite.height(), DEFAULT_SPEED, sprite);
    }

    void update() {
        accelerate();
        setFlightDir(targetDir);
        move();
        checkBounds();
    }

    void accelerate() {
        float angle = getDir();
        float rotatedAccX = (float)(accX * Math.cos(angle) - accY * Math.sin(angle));
        float rotatedAccY = (float)(accX * Math.sin(angle) + accY * Math.cos(angle));
        velX += rotatedAccX;
        velY += rotatedAccY;
    }

    void move() {
        float xStep = getXStep(), yStep = getYStep();
        for(int i = 0; i < Math.max(Math.abs(velX), Math.abs(velY)); i++) {
            x += xStep * Math.signum(velX);
            y += yStep * Math.signum(velY);
            handleCollisions();
        }
    }

    void setFlightDir(float angle) {
        float currentAngle = getDir();
        float diff = currentAngle - angle;
        float change = 0;
        if(Math.abs(diff) >= 5 * turnSpeed) {
            if(currentAngle > 0) {
                if(diff > 0 && diff < Math.PI) {
                    change = -turnSpeed;
                }
                else {
                    change = turnSpeed;
                }
            }
            else {
                if(diff < 0 && diff > -Math.PI) {
                    change = turnSpeed;
                }
                else {
                    change = -turnSpeed;
                }
            }
            setDir(currentAngle + change, true);
        }
        if(sprite.numTextures() >= 3) {
            int imageIndex = (change == 0 ? 0 : change < 0 ? 1 : 2);
            if (imageIndex == 0) {
                if (imageResetCounter == 0) {
                    sprite.setTexture(0);
                    imageResetCounter = IMAGE_RESET_FRAMES;
                }
            } else {
                sprite.setTexture(imageIndex);
            }
            if (imageResetCounter > 0) {
                imageResetCounter--;
            }
        }
    }

    boolean checkBounds() {
        if(x < boundX1 || x > boundX2 || y < boundY1 || y > boundY2) {
            outOfBounds();
            return true;
        }
        return false;
    }

    void outOfBounds() { isAlive = false; }

    void takeDamage() {
        isAlive = false;
    }

    boolean isColliding(Object obj) {
        if(collisionType == CollisionType.RECTANGLE && obj.collisionType == CollisionType.RECTANGLE) {
            return x - w / 2 < obj.x + w / 2 &&
                    x + w / 2 > obj.x - w / 2 &&
                    y - h / 2 < obj.y + h / 2 &&
                    y + h / 2 > obj.y - h / 2;
        }
        else {
            if(collisionType == CollisionType.LINE) {
                return collisionLine(x, y, x2, y2, obj);
            }
            else {
                return collisionLine(obj.x, obj.y, obj.x2, obj.y2, this);
            }
        }
    }

    ArrayList<Object> getCollisions() {
        ArrayList<Object> collisions = new ArrayList<>();
        for(Object obj : Game.room.objs){
            if(isColliding(obj)) {
                collisions.add(obj);
            }
        }
        return collisions;
    }

    void handleCollisions() {
        ArrayList<Object> collisions = getCollisions();
        for(Object obj : collisions) {
            collision(obj);
        }
    }

    /**call super.collision(type) for solid collisions**/
    @SuppressWarnings("incomplete-switch")
    void collision(Object obj) {
        switch(obj.type()) {
            case SOLID:
                solidCollision();
                break;
        }
    }

    private float getXStep() {
        return velY == 0 ? 1 : Math.abs(velX) > Math.abs(velY) ? 1 : Math.abs(velX / velY);
    }

    private float getYStep() {
        return velX == 0 ? 1 : Math.abs(velY) > Math.abs(velX) ? 1 : Math.abs(velY) / Math.abs(velX);
    }

    void solidCollision() {
        if(velX != 0) { x -= getXStep(); }
        if(velY != 0) { y -= getYStep(); }
        velX = 0;
        velY = 0;
    }

    float getDir() {
        return (float)Math.atan2(velY, velX);
    }

    void setDir(float angle, boolean adjustRotation) {
        double hypot = Math.hypot(velX, velY);
        velX = (float)(Math.cos(angle) * hypot);
        velY = (float)(Math.sin(angle) * hypot);
        if(adjustRotation) { adjustRotation(); }
    }

    void setVel(float speed, float angle, boolean adjustRotation) {
        velX = speed;
        velY = 0;
        setDir(angle, adjustRotation);
    }

    void setVel(float angle, boolean adjustRotation) {
        setVel(speed, angle, adjustRotation);
    }

    void adjustRotation() {
        sprite.rotation = (float)Math.atan2(velX, velY);
    }

    void setRotation(float rotation) {
        sprite.rotation = rotation;
    }

    static Object collisionPoint(float x, float y, Type type) {
        for(Object obj : Game.room.objs) {
            if(obj.type() == type && collisionPoint(x, y, obj)) {
                return obj;
            }
        }
        return null;
    }

    static boolean collisionPoint(float x, float y, Object obj) {
        return x > obj.x - obj.w / 2 && x < obj.x + obj.w / 2 && y > obj.y - obj.h / 2 && y < obj.y + obj.h / 2;
    }

    static boolean collisionLine(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        float denom = ((y4 - y3) * (x2 - x1)) -	((x4 - x3) * (y2 - y1));
        if (denom == 0) {
            return false;
        }
        else {
            float ua = (((x4 - x3) * (y1 - y3)) - ((y4 - y3) * (x1 - x3))) / denom;
            float ub = (((x2 - x1) * (y1 - y3)) - ((y2 - y1) * (x1 - x3))) / denom;
            return ua >= 0 && ua <= 1 && ub >= 0 && ub <= 1;
        }
    }

    static boolean collisionLine(float x1, float y1, float x2, float y2, Object obj) {
        for(int i = 0; i < 4; i++) {
            if(collisionLine(x1, y1, x2, y2, obj.x + (i % 2 == 0 ? -1 : 1) * obj.w / 2, obj.y + (i < 2 ? -1 : 1) * obj.h / 2, obj.x + (i + 1 % 2 == 0 ? -1 : 1) * obj.w / 2, obj.y + (i + 1 < 2 ? -1 : 1) * obj.h / 2)) {
                if(obj.type() == Type.ENEMY) {
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    static Object collisionLine(float x1, float y1, float x2, float y2, Type type) {
        for(Object obj : Game.room.objs) {
            if(obj.type() == type && collisionLine(x1, y1, x2, y2, obj)) {
                return obj;
            }
        }
        return null;
    }

    /*static boolean collisionLineRect(RectF rect, Object obj) {
        return collisionLine(rect.left, rect.top, rect.right, rect.top, obj) ||
                collisionLine(rect.right, rect.top, rect.right, rect.bottom, obj) ||
                collisionLine(rect.left, rect.bottom, rect.right, rect.bottom, obj) ||
                collisionLine(rect.left, rect.top, rect.left, rect.bottom, obj);
    }*/

    void negateScroll() {
        if(Game.room.scrollX != 0) {
            float temp = velX;
            velX = -Game.room.scrollX;
            for(int i = 0; i < Math.abs(velX); i++) {
                x += Math.signum(velX);
                handleCollisions();
            }
            velX = temp;
        }
        if(Game.room.scrollY != 0) {
            float temp = velY;
            velY = -Game.room.scrollY;
            for(int i = 0; i < Math.abs(velY); i++) {
                y += Math.signum(velY);
                handleCollisions();
            }
            velY = temp;
        }
    }

    void draw(Canvas canvas) {
        sprite.draw(getRect(), canvas);
    }

    @Override
    public int compareTo(Object obj) {
        return sprite.depth == obj.sprite.depth ? 0 : sprite.depth < obj.sprite.depth ? -1 : 1;
    }
}