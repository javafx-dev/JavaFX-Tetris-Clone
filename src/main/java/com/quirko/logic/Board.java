package com.quirko.logic;

public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateLeftBrick();

    boolean createNewBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    void updateLevel(Level toUpdate);

    ClearRow clearRows();

    Score getScore();

    Level getLevel();

    void newGame();
}
