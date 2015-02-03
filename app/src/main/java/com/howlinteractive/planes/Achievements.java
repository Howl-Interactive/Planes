package com.howlinteractive.planes;

import android.content.SharedPreferences;

import java.util.HashMap;

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

    private static HashMap<String, Counter> counters;
    private static HashMap<String, Integer> levels;
    private static HashMap<String, int[]> targets;

    static void load(SharedPreferences sharedPreferences) {
        Achievements.sharedPreferences = sharedPreferences;
        loadCounters();
        loadTargets();
        loadAchievements();
    }

    private static void loadCounters() {
        counters = new HashMap<>();
        counters.put("kills", new Counter(sharedPreferences.getInt("kills", 0)));
    }

    private static void loadTargets() {
        targets = new HashMap<>();
        targets.put("kills", new int[] { 5000, 5001, 54002 });
    }

    private static void loadAchievements() {
        levels = new HashMap<>();
        levels.put("kills", getNextTargetIndex("kills"));
    }

    static void save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(HashMap.Entry<String, Counter> entry : counters.entrySet()) {
            editor.putInt(entry.getKey(), entry.getValue().get());
        }
        editor.apply();
    }

    static int set(String key, int amount) {
        return counters.get(key).set(amount);
    }
    static int modify(String key, int amount) {
        return counters.get(key).modify(amount);
    }
    static int increment(String key) {
        return counters.get(key).increment();
    }
    static int decrement(String key) {
        return counters.get(key).decrement();
    }

    static HashMap<String, Integer> get() {
        HashMap<String, Integer> achievements = new HashMap<>();
        for(HashMap.Entry<String, Counter> entry : counters.entrySet()) {
            achievements.put(entry.getKey(), entry.getValue().get());
        }
        return achievements;
    }
    static int get(String key) {
        return counters.get(key).get();
    }
    static int getLevel(String key) { return levels.get(key); }

    static int getNextTargetIndex(String key) {
        float num = get(key);
        int[] achievements = targets.get(key);
        for(int i = 0; i < achievements.length; i++) {
            if(num < achievements[i]) {
                return i;
            }
        }
        return achievements.length;
    }

    static float getNextTarget(String key) {
        int[] achievements = targets.get(key);
        int index = getNextTargetIndex(key), numAchievements = achievements.length;
        return index == numAchievements ? achievements[numAchievements - 1] : achievements[index];
    }

    static float getPercent(String key) {
        return get(key) / getNextTarget(key);
    }
}
