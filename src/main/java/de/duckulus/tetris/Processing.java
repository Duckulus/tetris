package de.duckulus.tetris;

import de.duckulus.tetris.game.Piece;
import de.duckulus.tetris.game.PieceKind;
import de.duckulus.tetris.game.Tetris;
import de.duckulus.tetris.math.Vec2;
import de.duckulus.tetris.utils.StringUtils;
import de.duckulus.tetris.utils.Timer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.KeyEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Processing extends PApplet {

    private Tetris game;
    private Timer timer;
    private boolean paused = false;
    private boolean showGrid = true;
    private boolean showNext = true;
    private boolean showGhostPiece = true;

    @Override
    public void settings() {
        size(Constants.GRID_WIDTH + 500, Constants.GRID_HEIGHT + 105, PConstants.JAVA2D);
    }

    @Override
    public void setup() {
        windowTitle("Tetris");
        game = new Tetris();
        timer = new Timer();
    }

    @Override
    public void draw() {
        background(Color.WHITE.getRGB());

        drawGrid();
        drawBoard();
        drawCurrentPiece();
        drawGhostPiece();
        drawScore();
        drawNextPiece();
        drawLevel();
        drawLines();
        drawControls();

        if (timer.hasPassed(TimeUnit.SECONDS.toMillis(1) / 20) && !paused) {
            timer.reset();
            game.tick();
        }


    }


    public void drawGrid() {
        fill(Color.BLACK.getRGB());
        strokeWeight(1);
        if (showGrid) {
            for (int i = 1; i < Constants.WIDTH; i++) {
                line(i * Constants.BLOCK_SIZE + Constants.GRID_X_OFFSET, Constants.GRID_Y_OFFSET, i * Constants.BLOCK_SIZE + Constants.GRID_X_OFFSET, Constants.GRID_HEIGHT + Constants.GRID_Y_OFFSET);
            }
            for (int i = 1; i < Constants.HEIGHT; i++) {
                line(Constants.GRID_X_OFFSET, i * Constants.BLOCK_SIZE + Constants.GRID_Y_OFFSET, Constants.GRID_WIDTH + Constants.GRID_X_OFFSET, i * Constants.BLOCK_SIZE + Constants.GRID_Y_OFFSET);
            }
        }

        strokeWeight(3);
        line(Constants.GRID_X_OFFSET, Constants.GRID_Y_OFFSET, Constants.GRID_X_OFFSET, Constants.GRID_HEIGHT + Constants.GRID_Y_OFFSET);
        line(Constants.GRID_X_OFFSET, Constants.GRID_Y_OFFSET, Constants.GRID_WIDTH + Constants.GRID_X_OFFSET, Constants.GRID_Y_OFFSET);
        line(Constants.GRID_WIDTH + Constants.GRID_X_OFFSET, Constants.GRID_Y_OFFSET, Constants.GRID_WIDTH + Constants.GRID_X_OFFSET, Constants.GRID_HEIGHT + Constants.GRID_Y_OFFSET);
        line(Constants.GRID_X_OFFSET, Constants.GRID_HEIGHT + Constants.GRID_Y_OFFSET, Constants.GRID_WIDTH + Constants.GRID_X_OFFSET, Constants.GRID_HEIGHT + Constants.GRID_Y_OFFSET);
    }

    public void drawBoard() {
        for (int i = Constants.WIDTH * Constants.OUT_OF_SCREEN_LINES; i < game.getBoard().length; i++) {
            int x = i % Constants.WIDTH;
            int y = i / Constants.WIDTH - Constants.OUT_OF_SCREEN_LINES;
            if (game.getBoard()[i] != null) {
                drawBlock(x, y, game.getBoard()[i].getColor(), 255f, true);
            }
        }
    }

    public void drawPiece(Piece piece, float alpha, boolean glint) {
        if (piece == null) return;
        int x = piece.getLocation().x();
        int y = piece.getLocation().y() - Constants.OUT_OF_SCREEN_LINES;
        Vec2[] coords = piece.getPieceKind().getPieceCoordinates()[game.getRotationCount()];

        for (Vec2 coord : coords) {
            int blockX = x + coord.x();
            int blockY = y + coord.y();
            if (blockY >= 0) drawBlock(blockX, blockY, piece.getPieceKind().getColor(), alpha, glint);
        }
    }

    public void drawCurrentPiece() {
        drawPiece(game.getCurrentPiece(), 255, true);
    }

    public void drawGhostPiece() {
        if (showGhostPiece) drawPiece(game.getGhostPiece(), 50, false);
    }

    private void drawBlock(int blockX, int blockY, Color color, float alpha, boolean glint) {
        strokeWeight(2);
        fill(color.getRGB(), alpha);
        square(blockX * Constants.BLOCK_SIZE + Constants.GRID_X_OFFSET, blockY * Constants.BLOCK_SIZE + Constants.GRID_Y_OFFSET, Constants.BLOCK_SIZE);

        if (glint) {
            strokeWeight(0);
            int baseX = blockX * Constants.BLOCK_SIZE + Constants.GRID_X_OFFSET + 2;
            int baseY = blockY * Constants.BLOCK_SIZE + Constants.GRID_Y_OFFSET + 2;

            fill(color.darker().getRGB(), alpha);
            rect(baseX + Constants.BLOCK_SIZE - 5, baseY, 3, Constants.BLOCK_SIZE - 4);
            rect(baseX, baseY, Constants.BLOCK_SIZE - 3, 3);

            fill(color.brighter().getRGB(), alpha);
            rect(baseX, baseY, 3, Constants.BLOCK_SIZE -3);
            rect(baseX, baseY + Constants.BLOCK_SIZE - 5, Constants.BLOCK_SIZE - 4, 3);
        }
    }


    public void drawScore() {
        fill(Color.WHITE.getRGB());
        textSize(30);
        strokeWeight(3);

        rect(775, 100, 200, 100);

        fill(Color.BLACK.getRGB());
        text("SCORE", 800, 150);
        text(StringUtils.padLeftZeros(String.valueOf(game.getScore()), 6), 800, 175);
    }

    public void drawNextPiece() {
        PieceKind pieceKind = game.getNextPiece();
        int x = 825;
        int y = 500;
        int boxSize = 50;

        fill(Color.WHITE.getRGB());
        textSize(30);
        strokeWeight(3);
        rect(775, 400, 100, 150);

        fill(Color.BLACK.getRGB());
        text("NEXT", 790, 440);


        strokeWeight(1);
        fill(pieceKind.getColor().getRGB());
        if (showNext) {
            for (Vec2 pieceCoordinate : pieceKind.getPieceCoordinates()[0]) {
                square(x - 10 - ((float) boxSize / 2) + pieceCoordinate.x() * Constants.NEXT_PIECE_BLOCK_SIZE, y - ((float) boxSize / 2) + pieceCoordinate.y() * Constants.NEXT_PIECE_BLOCK_SIZE, Constants.NEXT_PIECE_BLOCK_SIZE);
            }
        }
    }

    public void drawLevel() {
        strokeWeight(3);
        textSize(30);

        fill(Color.WHITE.getRGB());
        rect(775, 600, 100, 100);

        fill(Color.BLACK.getRGB());
        text("LEVEL", 785, 650);
        text(game.getClearedLines() / 10, 815, 680);

    }

    public void drawLines() {
        fill(Color.WHITE.getRGB());
        strokeWeight(3);
        textSize(50);

        rect(250, 5, 500, 90);

        fill(Color.BLACK.getRGB());

        text("Lines - " + game.getClearedLines(), 400, 60);

    }

    public void drawControls() {
        fill(Color.WHITE.getRGB());
        strokeWeight(3);

        rect(5, 400, 240, 300);

        fill(Color.BLACK.getRGB());
        textSize(30);
        text("Controls", 10, 435);

        textSize(20);
        text("Move Left - LEFT ARROW", 10, 460);
        text("Move Right - RIGHT ARROW", 10, 485);
        text("Rotate - UP ARROW", 10, 510);
        text("Soft Drop - DOWN ARROW", 10, 535);
        text("Pause - ENTER", 10, 560);
        text("Toggle Grid - G", 10, 585);
        text("Toggle Next - N", 10, 610);
        text("Toggle Ghost - X", 10, 635);
        text("Restart - R", 10, 660);
        text("Exit - Esc", 10, 685);
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
        if (event.getKey() == PConstants.ENTER) {
            paused = !paused;
        }
        if (event.getKey() == 'g') {
            showGrid = !showGrid;
        }
        if (event.getKey() == 'n') {
            showNext = !showNext;
        }
        if (event.getKey() == 'x') {
            showGhostPiece = !showGhostPiece;
        }
        if (event.getKey() == 'r') {
            game = new Tetris();
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (event.getKeyCode() == PConstants.DOWN) {
            game.softDrop(false);
        }
    }
}
