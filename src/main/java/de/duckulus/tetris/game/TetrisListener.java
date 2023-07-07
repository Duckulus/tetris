package de.duckulus.tetris.game;

public interface TetrisListener {

    void onPieceLand();

    void onLineClear(int clearedAmount);

    void onGameOver();

}
