package de.duckulus.tetris.game;

import de.duckulus.tetris.Constants;
import de.duckulus.tetris.math.Vec2;

public class Tetris {

    private boolean[] board;
    private Piece currentPiece;
    private int rotationCount = 0;

    public Tetris() {
        board = new boolean[Constants.WIDTH * Constants.HEIGHT];
        currentPiece = new Piece(PieceKind.T, Vec2.of(5, 10));
    }

    public void gravityStep() {
        if (currentPiece == null) return;

        if (canMove(currentPiece.getPieceKind(), currentPiece.getLocation().add(Vec2.of(0, 1)), rotationCount)) {
            currentPiece.setLocation(currentPiece.getLocation().add(Vec2.of(0, 1)));

        } else {
            for (Vec2 coord : currentPiece.getPieceKind().getPieceCoordinates()[rotationCount]) {
                Vec2 blockLocation = currentPiece.getLocation().add(coord);
                board[blockLocation.y() * Constants.WIDTH + blockLocation.x()] = true;
            }
            currentPiece = null;
        }
    }

    public boolean canMove(PieceKind pieceKind, Vec2 location, int rotation) {
        Vec2[] coords = pieceKind.getPieceCoordinates()[rotation];

        boolean collision = false;
        for (Vec2 coord : coords) {
            Vec2 newLocation = location.add(coord);
            if (!isInbounds(newLocation) || board[newLocation.x() * newLocation.y()]) {
                collision = true;
            }
        }
        return !collision;
    }

    public boolean isInbounds(Vec2 coords) {
        return coords.x() >= 0 && coords.x() < Constants.WIDTH && coords.y() >= 0 && coords.y() < Constants.HEIGHT;
    }

    public void rotate() {
        if (currentPiece == null) return;

        int newRotationCount = (rotationCount + 1) % 4;
        if (canMove(currentPiece.getPieceKind(), currentPiece.getLocation(), newRotationCount)) {
            rotationCount = newRotationCount;
        }
    }

    public void moveLeft() {
        if (currentPiece == null) return;

        if (canMove(currentPiece.getPieceKind(), currentPiece.getLocation().add(Vec2.of(-1, 0)), rotationCount)) {
            currentPiece.setLocation(currentPiece.getLocation().add(Vec2.of(-1, 0)));
        }
    }

    public void moveRight() {
        if (currentPiece == null) return;

        if (canMove(currentPiece.getPieceKind(), currentPiece.getLocation().add(Vec2.of(1, 0)), rotationCount)) {
            currentPiece.setLocation(currentPiece.getLocation().add(Vec2.of(1, 0)));
        }
    }

    public boolean[] getBoard() {
        return board;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public int getRotationCount() {
        return rotationCount;
    }
}
