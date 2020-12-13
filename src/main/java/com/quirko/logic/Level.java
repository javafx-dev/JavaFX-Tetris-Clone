package com.quirko.logic;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Level {

    private int levelNumber;
    private int maxPoint;
    private int currentPoint;
    private int moveCount;
    private IntegerProperty target = new SimpleIntegerProperty(0);

    //This constructor sets information for the first level. After that, levels increases
    // automatically.
    public Level(int maxPoint) {
        this.levelNumber = 1;
        this.maxPoint = maxPoint;
        this.currentPoint = 0;
        this.moveCount = 0;
        target = new SimpleIntegerProperty(maxPoint);
    }



    public boolean completed(){
        return currentPoint >= maxPoint;
    }

    public String getName() {
        return levelNumber + ". Level";
    }

    public int getMaxPoint() {
        return maxPoint;
    }

    public double getCurrentPoint() {
        return currentPoint;
    }

    public int getMoveCount() {
        return moveCount;
    }

    @Override
    public String toString() {
        return "Level{" +
                "levelNumber=" + levelNumber +
                ", maxPoint=" + maxPoint +
                ", currentPoint=" + currentPoint +
                ", moveCount=" + moveCount +
                '}';
    }

    public void addPoint(int toBeAdded){
        currentPoint += toBeAdded;
    }

    public void resetLevel(){
        levelNumber = 0;
        currentPoint = 0;
        moveCount = 0;
        System.out.println("Level resettled");
    }

    public void addCount(int toBeAdded){
        moveCount += toBeAdded;
    }



    public double successRate(){
        return currentPoint / moveCount * 20;
    }

    public void upgradeLevel(){
        this.levelNumber++;
        this.maxPoint = (int) Math.round(maxPoint * 1.2);
        maxPoint = (maxPoint + 4) / 5 * 5;
        target.setValue( (maxPoint + 4) / 5 * 5);
        this.currentPoint = 0;
        this.moveCount = 0;
    }


    public IntegerProperty targetProperty() {
        return target;
    }


}
