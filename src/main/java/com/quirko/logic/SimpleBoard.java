package com.quirko.logic;

import com.quirko.logic.bricks.Brick;
import com.quirko.logic.bricks.BrickGenerator;
import com.quirko.logic.bricks.RandomBrickGenerator;
import com.quirko.logic.rotator.BrickRotator;
import com.quirko.logic.rotator.NextShapeInfo;

import java.awt.*;

public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    public static int colorCode;
    private Point currentOffset;
    private final Score score;
    private SuperPoint point;
    public static boolean RightTouched=false;
	public static boolean LeftTouched=false;

    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
        point = new SuperPoint();
        RightTouched=false;
        LeftTouched=false;
    }

    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        if(MatrixOperations.isSpecial(brickRotator.getCurrentShape())) colorCode=brickRotator.getCurrentShape()[1][1];
        else colorCode=0;

        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        if(MatrixOperations.isSpecial(brickRotator.getCurrentShape()))return false;
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            if(MatrixOperations.isVerySpecial(brickRotator.getCurrentShape()))
                LeftTouched = true; 
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        if(MatrixOperations.isSpecial(brickRotator.getCurrentShape()))return false;
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            if(MatrixOperations.isVerySpecial(brickRotator.getCurrentShape()))
                RightTouched = true; 
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        if(MatrixOperations.isSpecial(brickRotator.getCurrentShape())) colorCode=brickRotator.getCurrentShape()[1][1];
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }

    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(4, 0);
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    public Brick getBrick(){
        return brickGenerator.getBrick();
    }

    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0));
    }

    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;

    }

    public ClearColor clearColors() {
        ClearColor clearColor = MatrixOperations.checkRemoving2(currentGameMatrix,colorCode);
        currentGameMatrix = clearColor.getNewMatrix();
        return clearColor;

    }

    public SuperPoint getSuperPoint(){
        return point;
    }

    @Override
    public Score getScore(){ 
        return score;
    }


    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        point.reset();
        createNewBrick();
    }


    public boolean isTouched(){
        return RightTouched&&LeftTouched;
    }
    
    public void makeUntouched() {
        RightTouched = false;
        LeftTouched = false;
    }
}
