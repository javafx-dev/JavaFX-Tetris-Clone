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
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.bindSuperPoint(board.getSuperPoint().pointProperty());
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        ClearColor clearColor = null;
        

        if (!canMove) {
            board.mergeBrickToBackground();

            if(SimpleBoard.colorCode!=0){
                clearColor = board.clearColors();
                board.getScore().add(clearColor.getScoreBonus());
                if (board.createNewBrick()) {
                    viewGuiController.gameOver();
                }
            }

            else{
                clearRow = board.clearRows();
                if (clearRow.getLinesRemoved() > 0) {
                    board.getScore().add(clearRow.getScoreBonus());
                }
                if (board.createNewBrick()) {
                    viewGuiController.gameOver();
                }
            }
        
            if(board.isTouched()){
                    board.getSuperPoint().add(1);
                    SimpleBoard.RightTouched=SimpleBoard.LeftTouched=false;
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());

        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }

        if(SimpleBoard.colorCode!=0)
        return new DownData(clearRow, board.getViewData(), clearColor);
        else
        return new DownData(clearColor, board.getViewData(), clearRow);
        
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
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
