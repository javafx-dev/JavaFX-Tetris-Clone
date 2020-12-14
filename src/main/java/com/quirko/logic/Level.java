package com.quirko.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Level {

    private int levelNumber;
    private int maxLevel;
    private int maxPoint;
    private int currentPoint;
    private int moveCount;
    private IntegerProperty target;
    private IntegerProperty levelID;


    //This constructor sets information for the first level. After that, levels increases
    // automatically.
    public Level(int maxPoint) {
        this.levelNumber = 1;
        this.maxLevel = 1;
        this.maxPoint = maxPoint;
        this.currentPoint = 0;
        this.moveCount = 0;
        target = new SimpleIntegerProperty(maxPoint);
        levelID = new SimpleIntegerProperty(1);
    }


    public int getLevelNumber() {
        return levelNumber;
    }


    public int getMaxLevel() {
        return maxLevel;
    }


    public int getCurrentPoint() {
        return currentPoint;
    }


    public int getMoveCount() {
        return moveCount;
    }


    public void downgradeLevel() {
        this.levelNumber -= 1;
        this.currentPoint = 0;
        this.moveCount = 0;
        maxPoint /= 1.2;
        maxPoint /= 1.2;
        maxPoint = (maxPoint + 3) / 5 * 5;
        levelID.setValue(levelNumber);
        target.setValue(maxPoint);
    }


    public void levelUp() {
        System.out.println("Level UP by user");
        this.levelNumber++;
        this.maxPoint = (int) Math.round(maxPoint * 1.2);
        maxPoint *= 1.2;
        maxPoint = (maxPoint + 4) / 5 * 5;
        target.setValue(maxPoint);
        levelID.setValue(levelNumber);
        this.currentPoint = 0;
        this.moveCount = 0;
    }


    public boolean completed() {
        return currentPoint >= maxPoint;
    }


    public String getName() {
        return levelNumber + ". Level";
    }


    @Override
    public String toString() {
        return "Level{" +
                "levelNumber=" + levelNumber +
                ", maxPoint=" + maxPoint +
                ", currentPoint=" + currentPoint +
                ", moveCount=" + moveCount +
                ", maxLevel=" + maxLevel +
                '}';
    }


    public void addPoint(int toBeAdded) {
        currentPoint += toBeAdded;
    }


    public void resetLevel() {
        maxLevel = 1;
        levelNumber = 1;
        currentPoint = 0;
        moveCount = 0;
        maxPoint = 30;
        target.setValue(maxPoint);
        levelID.setValue(1);
        System.out.println("Level resettled");
    }


    public void addCount(int toBeAdded) {
        moveCount += toBeAdded;
    }


    public void upgradeLevel() {
        System.out.println("Level upgraded");
        this.levelNumber++;
        this.maxPoint = (int) Math.round(maxPoint * 1.2);
        maxPoint *= 1.2;
        maxPoint = (maxPoint + 4) / 5 * 5;
        target.setValue((maxPoint + 4) / 5 * 5);
        levelID.setValue(levelNumber);
        this.currentPoint = 0;
        this.moveCount = 0;
        this.maxLevel = levelNumber;
    }


    public boolean canLevelUp() {
        return maxLevel > levelNumber;
    }


    public boolean canLevelDown() {
        if (levelNumber == 1) return false;
        return maxLevel >= levelNumber;
    }


    public IntegerProperty targetProperty() {
        return target;
    }


    public IntegerProperty levelIDProperty() {
        return levelID;
    }

}
