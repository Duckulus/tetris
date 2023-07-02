package de.duckulus.tetris;

import de.duckulus.tetris.game.Piece;
import de.duckulus.tetris.game.Tetris;
import de.duckulus.tetris.math.Vec2;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.KeyEvent;

import java.awt.*;

public class Processing extends PApplet {

    private Tetris game;
    private Timer timer;
    private boolean down;

    @Override
    public void settings() {
        size(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, PConstants.JAVA2D);
    }

    @Override
    public void setup() {
        game = new Tetris();
        timer = new Timer();
    }

    @Override
    public void draw() {
        background(Color.WHITE.getRGB());
        fill(Color.CYAN.getRGB());

        drawGrid();
        drawBoard();
        drawCurrentPiece();

        if (timer.hasPassed(down ? 50 : 500)) {
            timer.reset();
            game.gravityStep();
        }
    }

    public void drawGrid() {
        fill(Color.BLACK.getRGB());
        for (int i = 1; i < Constants.WIDTH; i++) {
            line(i * Constants.BLOCK_SIZE, 0, i * Constants.BLOCK_SIZE, Constants.SCREEN_HEIGHT);
        }
        for (int i = 1; i < Constants.HEIGHT; i++) {
            line(0, i * Constants.BLOCK_SIZE, Constants.SCREEN_WIDTH, i * Constants.BLOCK_SIZE);
        }
    }

    public void drawBoard() {
        for (int i = 0; i < game.getBoard().length; i++) {
            int x = i % Constants.WIDTH;
            int y = i / Constants.WIDTH;
            if (game.getBoard()[i] != null) {
                fill(game.getBoard()[i].getRgb());
                square(x * Constants.BLOCK_SIZE, y * Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
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
            square(blockX * Constants.BLOCK_SIZE, blockY * Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
        }
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
            down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (event.getKeyCode() == PConstants.DOWN) {
            down = false;
        }
    }
}
