package de.duckulus.tetris.game;

import de.duckulus.tetris.math.Vec2;

import java.awt.*;

public enum PieceKind {
    O(Color.YELLOW.getRGB(), new Vec2[][]{
            {
                    Vec2.of(0, 0),
                    Vec2.of(0, 1),
                    Vec2.of(1, 0),
                    Vec2.of(1, 1)
            }
    }),
    T(Color.MAGENTA.getRGB(), new Vec2[][]{
            {
                    Vec2.of(1, 0),
                    Vec2.of(0, 1),
                    Vec2.of(1, 1),
                    Vec2.of(2, 1)
            },
            {
                    Vec2.of(1, 0),
                    Vec2.of(1, 1),
                    Vec2.of(1, 2),
                    Vec2.of(2, 1)
            },
            {
                    Vec2.of(0, 1),
                    Vec2.of(1, 1),
                    Vec2.of(2, 1),
                    Vec2.of(1, 2)
            },
            {
                    Vec2.of(1, 0),
                    Vec2.of(1, 1),
                    Vec2.of(1, 2),
                    Vec2.of(0, 1)
            }
    });

    private final int rgb;
    private final Vec2[][] pieceCoordinates;

    PieceKind(int rgb, Vec2[][] pieceCoordinates) {
        this.rgb = rgb;
        this.pieceCoordinates = pieceCoordinates;
    }


    public int getRgb() {
        return rgb;
    }

    public Vec2[][] getPieceCoordinates() {
        return pieceCoordinates;
    }
}
