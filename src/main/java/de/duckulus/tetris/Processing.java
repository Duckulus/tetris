package de.duckulus.tetris;

import de.duckulus.tetris.game.Piece;
import de.duckulus.tetris.game.PieceKind;
import de.duckulus.tetris.game.Tetris;
import de.duckulus.tetris.math.Vec2;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.KeyEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Processing extends PApplet {

    private Tetris game;
    private Timer timer;

    @Override
    public void settings() {
        size(Constants.GRID_WIDTH + 500, Constants.GRID_HEIGHT + 105, PConstants.JAVA2D);
    }

    @Override
    public void setup() {
        game = new Tetris();
        timer = new Timer();
    }

    @Override
    public void draw() {
        background(Color.WHITE.getRGB());

        drawGrid();
        drawBoard();
        drawCurrentPiece();
        drawNextPiece();
        drawLevel();
        drawLines();

        if (timer.hasPassed(TimeUnit.SECONDS.toMillis(1) / 20)) {
            timer.reset();
            game.tick();
        }


    }


    public void drawGrid() {
        fill(Color.BLACK.getRGB());
        strokeWeight(1);
        for (int i = 1; i < Constants.WIDTH; i++) {
            line(i * Constants.BLOCK_SIZE + Constants.GRID_X_OFFSET, Constants.GRID_Y_OFFSET, i * Constants.BLOCK_SIZE + Constants.GRID_X_OFFSET, Constants.GRID_HEIGHT + Constants.GRID_Y_OFFSET);
        }
        for (int i = 1; i < Constants.HEIGHT; i++) {
            line(Constants.GRID_X_OFFSET, i * Constants.BLOCK_SIZE + Constants.GRID_Y_OFFSET, Constants.GRID_WIDTH + Constants.GRID_X_OFFSET, i * Constants.BLOCK_SIZE + Constants.GRID_Y_OFFSET);
        }

        strokeWeight(3);
        line(Constants.GRID_X_OFFSET, Constants.GRID_Y_OFFSET, Constants.GRID_X_OFFSET, Constants.GRID_HEIGHT + Constants.GRID_Y_OFFSET);
        line(Constants.GRID_X_OFFSET, Constants.GRID_Y_OFFSET, Constants.GRID_WIDTH + Constants.GRID_X_OFFSET, Constants.GRID_Y_OFFSET);
        line(Constants.GRID_WIDTH + Constants.GRID_X_OFFSET, Constants.GRID_Y_OFFSET, Constants.GRID_WIDTH + Constants.GRID_X_OFFSET, Constants.GRID_HEIGHT + Constants.GRID_Y_OFFSET);
        line(Constants.GRID_X_OFFSET, Constants.GRID_HEIGHT + Constants.GRID_Y_OFFSET, Constants.GRID_WIDTH + Constants.GRID_X_OFFSET, Constants.GRID_HEIGHT + Constants.GRID_Y_OFFSET);
    }

    public void drawBoard() {
        strokeWeight(1);
        for (int i = 0; i < game.getBoard().length; i++) {
            int x = i % Constants.WIDTH;
            int y = i / Constants.WIDTH;
            if (game.getBoard()[i] != null) {
                fill(game.getBoard()[i].getRgb());
                square(x * Constants.BLOCK_SIZE + Constants.GRID_X_OFFSET, y * Constants.BLOCK_SIZE + Constants.GRID_Y_OFFSET, Constants.BLOCK_SIZE);
            }
        }
    }

    public void drawCurrentPiece() {
        if (game.getCurrentPiece() == null) return;

        Piece piece = game.getCurrentPiece();
        int x = piece.getLocation().x();
        int y = piece.getLocation().y();
        Vec2[] coords = piece.getPieceKind().getPieceCoordinates()[game.getRotationCount()];

        fill(piece.getPieceKind().getRgb());
        for (Vec2 coord : coords) {
            int blockX = x + coord.x();
            int blockY = y + coord.y();
            square(blockX * Constants.BLOCK_SIZE + Constants.GRID_X_OFFSET, blockY * Constants.BLOCK_SIZE + Constants.GRID_Y_OFFSET, Constants.BLOCK_SIZE);
        }
    }

    public void drawNextPiece() {
        PieceKind pieceKind = game.getNextPiece();
        int x = 825;
        int y = 500;
        int boxSize = 50;

        fill(Color.WHITE.getRGB());
        textSize(30);
        strokeWeight(3);
        rect(x - boxSize, y - boxSize-50, boxSize * 2, boxSize * 2+50);

        fill(Color.BLACK.getRGB());
        text("NEXT", 790, 440);


        strokeWeight(1);
        fill(pieceKind.getRgb());
        for (Vec2 pieceCoordinate : pieceKind.getPieceCoordinates()[0]) {
            square(x - 10 - ((float) boxSize / 2) + pieceCoordinate.x() * Constants.NEXT_PIECE_BLOCK_SIZE, y - ((float) boxSize / 2) + pieceCoordinate.y() * Constants.NEXT_PIECE_BLOCK_SIZE, Constants.NEXT_PIECE_BLOCK_SIZE);
        }
    }

    public void drawLevel() {
        strokeWeight(3);
        textSize(30);

        fill(Color.WHITE.getRGB());
        rect(775, 600, 100, 100);

        fill(Color.BLACK.getRGB());
        text("LEVEL", 785, 650);
        text(game.getClearedLines()/10, 815, 680);

    }

    public void drawLines() {
        fill(Color.WHITE.getRGB());
        strokeWeight(3);
        textSize(50);

        rect(250, 5, 500, 90);

        fill(Color.BLACK.getRGB());

        text("Lines - " + game.getClearedLines(), 400, 60);

    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == PConstants.UP) {
            game.rotate();
        }
        if (event.getKeyCode() == PConstants.LEFT) {
            game.moveLeft();
        }
        if (event.getKeyCode() == PConstants.RIGHT) {
            game.moveRight();
        }
        if (event.getKeyCode() == PConstants.DOWN) {
            game.softDrop(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (event.getKeyCode() == PConstants.DOWN) {
            game.softDrop(false);
        }
    }
}
