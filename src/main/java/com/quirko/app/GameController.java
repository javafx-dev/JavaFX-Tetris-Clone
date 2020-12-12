package com.quirko.app;

import com.quirko.gui.GuiController;
import com.quirko.logic.*;
import com.quirko.logic.events.EventSource;
import com.quirko.logic.events.InputEventListener;
import com.quirko.logic.events.MoveEvent;

public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);

    private final GuiController viewGuiController;
    private Level level;


    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        //Maxpoint for the first level is 250.
        level = new Level(250);
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            level.addCount(1);
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
                level.addPoint(clearRow.getScoreBonus());
            }
            if (board.createNewBrick()) {
                System.out.println(level.getName() + " is over. You failed.");
                viewGuiController.gameOver();
                createNewGame(false);
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());
            System.out.println(level);
        } else {
            //this is when player uses down arrow.
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
        if(level.completed()){
            System.out.println(level.getName() + " is completed.");
            System.out.println(level.successRate());
            createNewGame(true);
        }
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }


    @Override
    public void createNewGame(boolean isNewLevel) {
        if(isNewLevel){
            level.upgradeLevel();
        }
        else {
            level.resetLevel();
        }
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
