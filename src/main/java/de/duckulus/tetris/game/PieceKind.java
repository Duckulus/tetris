package de.duckulus.tetris.game;

import de.duckulus.tetris.math.Vec2;

import java.awt.*;

public enum PieceKind {
    I(Color.CYAN.getRGB(), new Vec2[][]{
            {
                    Vec2.of(0, 1),
                    Vec2.of(1, 1),
                    Vec2.of(2, 1),
                    Vec2.of(3, 1)
            },
            {
                    Vec2.of(2, 0),
                    Vec2.of(2, 1),
                    Vec2.of(2, 2),
                    Vec2.of(2, 3)
            },
            {
                    Vec2.of(0, 2),
                    Vec2.of(1, 2),
                    Vec2.of(2, 2),
                    Vec2.of(3, 2)
            },
            {
                    Vec2.of(1, 0),
                    Vec2.of(1, 1),
                    Vec2.of(1, 2),
                    Vec2.of(1, 3)
            }
    }),
    J(Color.BLUE.getRGB(), new Vec2[][]{
            {
                    Vec2.of(0, 0),
                    Vec2.of(0, 1),
                    Vec2.of(1, 1),
                    Vec2.of(2, 1)
            },
            {
                    Vec2.of(1, 0),
                    Vec2.of(2, 0),
                    Vec2.of(1, 1),
                    Vec2.of(1, 2)
            },
            {
                    Vec2.of(0, 1),
                    Vec2.of(1, 1),
                    Vec2.of(2, 1),
                    Vec2.of(2, 2)
            },
            {
                    Vec2.of(1, 0),
                    Vec2.of(1, 1),
                    Vec2.of(1, 2),
                    Vec2.of(0, 2)
            }
    }),
    L(Color.ORANGE.getRGB(), new Vec2[][]{
            {
                    Vec2.of(2, 0),
                    Vec2.of(0, 1),
                    Vec2.of(1, 1),
                    Vec2.of(2, 1)
            },
            {
                    Vec2.of(1, 0),
                    Vec2.of(2, 2),
                    Vec2.of(1, 1),
                    Vec2.of(1, 2)
            },
            {
                    Vec2.of(0, 1),
                    Vec2.of(1, 1),
                    Vec2.of(2, 1),
                    Vec2.of(0, 2)
            },
            {
                    Vec2.of(1, 0),
                    Vec2.of(1, 1),
                    Vec2.of(1, 2),
                    Vec2.of(0, 0)
            }
    }),
    O(Color.YELLOW.getRGB(), new Vec2[][]{
            {
                    Vec2.of(0, 0),
                    Vec2.of(0, 1),
                    Vec2.of(1, 0),
                    Vec2.of(1, 1)
            },
            {
                    Vec2.of(0, 0),
                    Vec2.of(0, 1),
                    Vec2.of(1, 0),
                    Vec2.of(1, 1)
            },
            {
                    Vec2.of(0, 0),
                    Vec2.of(0, 1),
                    Vec2.of(1, 0),
                    Vec2.of(1, 1)
            },
            {
                    Vec2.of(0, 0),
                    Vec2.of(0, 1),
                    Vec2.of(1, 0),
                    Vec2.of(1, 1)
            }
    }),
    S(Color.GREEN.getRGB(), new Vec2[][]{
            {
                    Vec2.of(1, 0),
                    Vec2.of(2, 0),
                    Vec2.of(0, 1),
                    Vec2.of(1, 1)
            },
            {
                    Vec2.of(1, 0),
                    Vec2.of(1, 1),
                    Vec2.of(2, 1),
                    Vec2.of(2, 2)
            },
            {
                    Vec2.of(1, 1),
                    Vec2.of(2, 1),
                    Vec2.of(1, 2),
                    Vec2.of(0, 2)
            },
            {
                    Vec2.of(0, 0),
                    Vec2.of(0, 1),
                    Vec2.of(1, 1),
                    Vec2.of(1, 2)
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
    }),
    Z(Color.RED.getRGB(), new Vec2[][]{
            {
                    Vec2.of(0, 0),
                    Vec2.of(1, 0),
                    Vec2.of(1, 1),
                    Vec2.of(2, 1)
            },
            {
                    Vec2.of(2, 0),
                    Vec2.of(1, 1),
                    Vec2.of(2, 1),
                    Vec2.of(1, 2)
            },
            {
                    Vec2.of(0, 1),
                    Vec2.of(1, 1),
                    Vec2.of(1, 2),
                    Vec2.of(2, 2)
            },
            {
                    Vec2.of(1, 0),
                    Vec2.of(1, 1),
                    Vec2.of(0, 1),
                    Vec2.of(0, 2)
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
