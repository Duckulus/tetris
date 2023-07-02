package de.duckulus.tetris.game;

import de.duckulus.tetris.math.Vec2;

public class Piece {

    private PieceKind pieceKind;
    private Vec2 location;

    public Piece(PieceKind pieceKind, Vec2 location) {
        this.pieceKind = pieceKind;
        this.location = location;
    }

    public PieceKind getPieceKind() {
        return pieceKind;
    }

    public Vec2 getLocation() {
        return location;
    }

    public void setLocation(Vec2 location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "pieceKind=" + pieceKind +
                ", location=" + location +
                '}';
    }
}
