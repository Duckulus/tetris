package de.duckulus.tetris.game;

import de.duckulus.tetris.Constants;
import de.duckulus.tetris.math.Vec2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Tetris {

    private final PieceKind[] board;
    private Piece currentPiece;
    private Piece ghostPiece;
    private int rotationCount = 0;
    private final ArrayList<PieceKind> bag;
    int tick = 0;
    private boolean softDrop = false;
    private int clearedLines = 0;
    private int score = 0;
    private boolean gameOver = false;

    public Tetris() {
        board = new PieceKind[Constants.WIDTH * Constants.HEIGHT];
        bag = new ArrayList<>();

        refillBag();
        spawnPiece();
    }

    public void tick() {
        if (gameOver) {
            currentPiece = null;
            return;
        }

        tick++;
        int level = clearedLines / 10;
        int dropRate = Math.max(1, 10 - level);
        if (softDrop || tick % dropRate == 0) {
            gravityStep();
        }
    }

    public void rotate() {
        if (currentPiece == null) return;

        int newRotationCount = (rotationCount + 1) % 4;
        if (canMove(currentPiece.getPieceKind(), currentPiece.getLocation(), newRotationCount)) {
            rotationCount = newRotationCount;
        }
        updateGhostPiece();
    }

    public void moveLeft() {
        if (currentPiece == null) return;

        if (canMove(currentPiece.getPieceKind(), currentPiece.getLocation().add(Vec2.of(-1, 0)), rotationCount)) {
            currentPiece.setLocation(currentPiece.getLocation().add(Vec2.of(-1, 0)));
        }
        updateGhostPiece();
    }

    public void moveRight() {
        if (currentPiece == null) return;

        if (canMove(currentPiece.getPieceKind(), currentPiece.getLocation().add(Vec2.of(1, 0)), rotationCount)) {
            currentPiece.setLocation(currentPiece.getLocation().add(Vec2.of(1, 0)));
        }
        updateGhostPiece();
    }

    public void softDrop(boolean softDrop) {
        this.softDrop = softDrop;
    }

    private void refillBag() {
        bag.addAll(Arrays.asList(PieceKind.values()));
        Collections.shuffle(bag);
    }

    private void spawnPiece() {
        rotationCount = 0;
        PieceKind pieceKind = bag.get(0);
        if (!canMove(pieceKind, Vec2.of(Constants.WIDTH / 2, 0), 0)) {
            gameOver = true;
        }
        bag.remove(pieceKind);
        if (bag.isEmpty()) {
            refillBag();
        }
        currentPiece = new Piece(pieceKind, Vec2.of(Constants.WIDTH / 2, 0));
        updateGhostPiece();
    }

    private void gravityStep() {
        if (currentPiece == null) return;

        if (canMove(currentPiece.getPieceKind(), currentPiece.getLocation().add(Vec2.of(0, 1)), rotationCount)) {
            currentPiece.setLocation(currentPiece.getLocation().add(Vec2.of(0, 1)));

        } else {
            for (Vec2 coord : currentPiece.getPieceKind().getPieceCoordinates()[rotationCount]) {
                Vec2 blockLocation = currentPiece.getLocation().add(coord);
                board[blockLocation.y() * Constants.WIDTH + blockLocation.x()] = currentPiece.getPieceKind();
            }
            currentPiece = null;
            clearLines();
            spawnPiece();
        }
    }

    private void updateGhostPiece() {
        if (currentPiece == null) return;
        Piece ghost = currentPiece.clone();
        while (canMove(ghost.getPieceKind(), ghost.getLocation().add(Vec2.of(0, 1)), rotationCount)) {
            ghost.setLocation(ghost.getLocation().add(Vec2.of(0, 1)));
        }
        ghostPiece = ghost;
    }

    private void clearLines() {

        int clearedAmount = 0;
        int firstCleared = -1;

        //count how many lines have been filled up and clear them
        for (int i = 0; i < Constants.HEIGHT; i++) {
            boolean cleared = true;
            for (int j = Constants.WIDTH * i; j < Constants.WIDTH * (i + 1); j++) {
                if (board[j] == null) {
                    cleared = false;
                    break;
                }
            }

            if (cleared) {
                if (clearedAmount == 0) firstCleared = i;
                clearedAmount += 1;
                for (int j = Constants.WIDTH * i; j < Constants.WIDTH * (i + 1); j++) {
                    board[j] = null;
                }
            }
        }

        clearedLines += clearedAmount;
        int level = clearedLines / 10;
        score += (level + 1) * switch (clearedAmount) {
            case 1 -> 40;
            case 2 -> 100;
            case 3 -> 300;
            case 4 -> 1200;
            default -> 0;
        };

        // move all the lines above the first cleared line down by how many lines have been cleared
        for (int i = firstCleared - 1; i >= 0; i--) {
            for (int j = Constants.WIDTH * i; j < Constants.WIDTH * (i + 1); j++) {
                board[j + Constants.WIDTH * clearedAmount] = board[j];
                board[j] = null;
            }
        }
    }

    private boolean canMove(PieceKind pieceKind, Vec2 location, int rotation) {
        Vec2[] coords = pieceKind.getPieceCoordinates()[rotation];

        boolean collision = false;
        for (Vec2 coord : coords) {
            Vec2 newLocation = location.add(coord);
            if (!isInbounds(newLocation) || board[newLocation.y() * Constants.WIDTH + newLocation.x()] != null) {
                collision = true;
            }
        }
        return !collision;
    }

    private boolean isInbounds(Vec2 coords) {
        return coords.x() >= 0 && coords.x() < Constants.WIDTH && coords.y() >= 0 && coords.y() < Constants.HEIGHT;
    }

    public PieceKind[] getBoard() {
        return board;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public Piece getGhostPiece() {
        return ghostPiece;
    }

    public int getRotationCount() {
        return rotationCount;
    }

    public PieceKind getNextPiece() {
        return bag.get(0);
    }

    public int getClearedLines() {
        return clearedLines;
    }

    public int getScore() {
        return score;
    }
}
