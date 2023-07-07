package de.duckulus.tetris.game;

import de.duckulus.tetris.math.Vec2;

import java.awt.*;

public enum PieceKind {
    I(Color.CYAN, new Vec2[][]{
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
    J(Color.BLUE, new Vec2[][]{
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
    L(Color.ORANGE, new Vec2[][]{
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
    O(Color.YELLOW, new Vec2[][]{
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
    S(Color.GREEN, new Vec2[][]{
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
    T(Color.MAGENTA, new Vec2[][]{
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
    Z(Color.RED, new Vec2[][]{
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

    private final Color color;
    private final Vec2[][] pieceCoordinates;

    PieceKind(Color color, Vec2[][] pieceCoordinates) {
        this.color = color;
        this.pieceCoordinates = pieceCoordinates;
    }


    public Color getColor() {
        return color;
    }

    public Vec2[][] getPieceCoordinates() {
        return pieceCoordinates;
    }
}
