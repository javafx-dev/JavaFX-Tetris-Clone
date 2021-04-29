package com.quirko.app;

import com.quirko.gui.GuiController;
import com.quirko.logic.*;
import com.quirko.logic.events.EventSource;
import com.quirko.logic.events.InputEventListener;
import com.quirko.logic.events.MoveEvent;

public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);

    private final GuiController viewGuiController;

    public GameController(GuiController c) {
        System.out.println("GameController.GameController()");

        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());

        System.out.println("GameController.GameController()2");
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        System.out.println("GameController.onDownEvent()");

        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());

        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }

        System.out.println("GameController.onDownEvent()2");
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        System.out.println("GameController.onLeftEvent()");

        board.moveBrickLeft();

        System.out.println("GameController.onLeftEvent()2");
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        System.out.println("GameController.onRightEvent()");

        board.moveBrickRight();

        System.out.println("GameController.onRightEvent()2");
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        System.out.println("GameController.onRotateEvent()");

        board.rotateLeftBrick();
        System.out.println("GameController.onRotateEvent()2");
        return board.getViewData();
    }


    @Override
    public void createNewGame() {
        System.out.println("GameController.createNewGame()");

        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());

        System.out.println("GameController.createNewGame()2");
    }
}
