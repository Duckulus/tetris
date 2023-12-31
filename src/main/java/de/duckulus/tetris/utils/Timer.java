package de.duckulus.tetris.utils;

public class Timer {

    private Long time;

    public Timer() {
        time = System.currentTimeMillis();
    }

    public Timer(long time) {
        this.time = time;
    }

    public void reset() {
        time = System.currentTimeMillis();
    }

    public boolean hasPassed(long millis) {
        return millis <= (System.currentTimeMillis() - time);
    }

}
