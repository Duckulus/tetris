package de.duckulus.tetris.game;

import de.duckulus.tetris.Constants;
import de.duckulus.tetris.math.Vec2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Tetris {

    private final boolean[] board;
    private Piece currentPiece;
    private int rotationCount = 0;
    private final ArrayList<PieceKind> bag;

    public Tetris() {
        board = new boolean[Constants.WIDTH * Constants.HEIGHT];
        bag = new ArrayList<>();

        refillBag();
        spawnPiece();
    }

    private void refillBag() {
        bag.addAll(Arrays.asList(PieceKind.values()));
        Collections.shuffle(bag);
    }

    private void spawnPiece() {
        if (bag.isEmpty()) {
            refillBag();
        }
        PieceKind pieceKind = bag.get(0);
        bag.remove(pieceKind);
        currentPiece = new Piece(pieceKind, Vec2.of(Constants.WIDTH / 2, 0));
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
            spawnPiece();
        }
    }

    public boolean canMove(PieceKind pieceKind, Vec2 location, int rotation) {
        Vec2[] coords = pieceKind.getPieceCoordinates()[rotation];

        boolean collision = false;
        for (Vec2 coord : coords) {
            Vec2 newLocation = location.add(coord);
            if (!isInbounds(newLocation) || board[newLocation.y() * Constants.WIDTH + newLocation.x()]) {
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
