package de.duckulus.tetris.game;

import de.duckulus.tetris.Constants;
import de.duckulus.tetris.math.Vec2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tetris {

    private PieceKind[] board;
    private PieceKind[] tempBoard;
    private Piece currentPiece;
    private Piece ghostPiece;
    private int rotationCount = 0;
    private final ArrayList<PieceKind> bag;
    int tick = 0;
    private boolean softDrop = false;
    private int clearedAmount = 0;
    private int score = 0;
    private boolean gameOver = false;
    private boolean cleared = false;
    private final ArrayList<Integer> clearedLines = new ArrayList<>();
    private final List<TetrisListener> listeners;


    public Tetris() {
        board = new PieceKind[Constants.WIDTH * Constants.HEIGHT_TOTAL];
        bag = new ArrayList<>();
        listeners = new ArrayList<>();

        refillBag();
        spawnPiece();
    }

    public void tick() {
        tick++;


        if (gameOver) {
            currentPiece = null;
            return;
        }

        if (cleared) {
            cleared = false;
            board = tempBoard.clone();
            spawnPiece();
        } else {
            int level = clearedAmount / 10;
            int dropRate = Math.max(1, 10 - level);
            if (softDrop || tick % dropRate == 0) {
                gravityStep();
            }
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
            for (TetrisListener listener : listeners) {
                listener.onGameOver();
            }
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
            for (TetrisListener listener : listeners) {
                listener.onPieceLand();
            }
            currentPiece = null;
            clearLines();
            if (!cleared) {
                spawnPiece();
            }
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
        clearedLines.clear();

        int clearedAmount = 0;
        PieceKind[] oldBoard = board.clone();

        for (int i = 0; i < Constants.HEIGHT_TOTAL; i++) {
            boolean isFull = true;
            for (int j = Constants.WIDTH * i; j < Constants.WIDTH * (i + 1); j++) {
                if (board[j] == null) {
                    isFull = false;
                    break;
                }
            }

            if (isFull) {
                clearedLines.add(i);
                clearLine(i);
                clearedAmount++;
            }
        }



        if (clearedAmount > 0) {
            for (TetrisListener listener : listeners) {
                listener.onLineClear(clearedAmount);
            }

            cleared = true;
            tempBoard = board.clone();
            board = oldBoard;
        }

        this.clearedAmount += clearedAmount;
        int level = this.clearedAmount / 10;
        score += (level + 1) * switch (clearedAmount) {
            case 1 -> 40;
            case 2 -> 100;
            case 3 -> 300;
            case 4 -> 1200;
            default -> 0;
        };
    }

    private void clearLine(int lineIndex) {
        for (int i = Constants.WIDTH * lineIndex; i < Constants.WIDTH * (lineIndex + 1); i++) {
            board[i] = null;
        }

        for (int i = lineIndex - 1; i >= 0; i--) {
            for (int j = Constants.WIDTH * i; j < Constants.WIDTH * (i + 1); j++) {
                board[j + Constants.WIDTH] = board[j];
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
        return coords.x() >= 0 && coords.x() < Constants.WIDTH && coords.y() >= 0 && coords.y() < Constants.HEIGHT_TOTAL;
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

    public int getClearedAmount() {
        return clearedAmount;
    }

    public int getScore() {
        return score;
    }

    public boolean isCleared() {
        return cleared;
    }

    public ArrayList<Integer> getClearedLines() {
        return clearedLines;
    }

    public void registerListener(TetrisListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(TetrisListener listener) {
        listeners.remove(listener);
    }

}
