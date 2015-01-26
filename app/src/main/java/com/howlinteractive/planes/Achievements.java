package com.howlinteractive.planes;

import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by jacobmacdonald on 1/24/15.
 */
public final class Achievements {

    private static class Counter {

        private int value;

        Counter(int def) { value = def; }
        Counter() { this(0); }

        int set(int value) { this.value = value; return value; }
        int modify(int amount) { value += amount; return value; }
        int increment() { return ++value; }
        int decrement() { return --value; }

        int get() { return value; }
    }

    private static SharedPreferences sharedPreferences;

    private static HashMap<String, Counter> achievements;

    static void load(SharedPreferences sharedPreferences) {
        Achievements.sharedPreferences = sharedPreferences;
        achievements = new HashMap<>();
        achievements.put("kills", new Counter(sharedPreferences.getInt("kills", 0)));
    }

    static void save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(HashMap.Entry<String, Counter> entry : achievements.entrySet()) {
            editor.putInt(entry.getKey(), entry.getValue().get());
        }
        editor.commit();
    }

    static int set(String key, int amount) {
        return achievements.get(key).set(amount);
    }
    static int modify(String key, int amount) {
        return achievements.get(key).modify(amount);
    }
    static int increment(String key) {
        return achievements.get(key).increment();
    }
    static int decrement(String key) {
        return achievements.get(key).decrement();
    }

    static HashMap<String, Integer> get() {
        HashMap<String, Integer> ach = new HashMap<>();
        for(HashMap.Entry<String, Counter> entry : achievements.entrySet()) {
            ach.put(entry.getKey(), entry.getValue().get());
        }
        return ach;
    }
    static int get(String key) {
        return achievements.get(key).get();
    }
}
