package de.duckulus.tetris.math;

import java.util.Arrays;

public class Vec2 {

    private final int[] coordinates;

    public Vec2(int x, int y) {
        this.coordinates = new int[]{x, y};
    }

    public int x() {
        return coordinates[0];
    }

    public int y() {
        return coordinates[1];
    }

    public Vec2 add(Vec2 other) {
        return new Vec2(coordinates[0] + other.x(), coordinates[1] + other.y());
    }

    public static Vec2 of(int x, int y) {
        return new Vec2(x, y);
    }

    @Override
    public String toString() {
        return "Vec2{" +
                "coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
