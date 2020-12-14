package com.quirko.logic;

import org.junit.Test;
import static org.junit.Assert.*;

public class levelTest {

    @Test
    public void levelNumberTest() {
        Level level = new Level(50);
        level.upgradeLevel();
        level.upgradeLevel();
        //Level started from 1 and we upgraded 2 levels. Level number should be 3.
        assertEquals(3, level.getLevelNumber());
    }


    @Test
    public void levelGoDownTest() {
        Level level = new Level(50);
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.downgradeLevel();
        level.downgradeLevel();
        //Level started from 1 and we upgraded 4 levels and downgraded it 2 times. Level number should be 3.
        assertEquals(3, level.getLevelNumber());
    }


    @Test
    public void levelGoDownMaxReachedLevelTest() {
        Level level = new Level(50);
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.downgradeLevel();
        level.downgradeLevel();
        //Level started from 1 and we upgraded 4 levels and downgraded it 2 times.
        //But max reached level should return 5. We keep track of max reached level too.
        assertEquals(5, level.getMaxLevel());
    }


    @Test
    public void levelGoUpReachedLevelTest() {
        /*
        Note: upgradeLevel function is only called when player finishes that level
        FOR THE FIRST TIME. After that, player can play desired achieved level again.
         */
        Level level = new Level(50);
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.downgradeLevel();
        level.downgradeLevel();
        level.downgradeLevel();
        level.levelUp();
        level.levelUp();
        //Level started from 1 and we upgraded 4 levels and downgraded it 3 times, and leveled up 2 times.
        assertEquals(4, level.getLevelNumber());
    }


    @Test
    public void levelGoUpMaxReachedLevelTest() {
        /*
        Note: upgradeLevel function is only called when player finishes that level
        FOR THE FIRST TIME. After that, player can play desired achieved level again.
         */
        Level level = new Level(50);
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.downgradeLevel();
        level.downgradeLevel();
        level.downgradeLevel();
        level.levelUp();
        level.levelUp();
        //Level started from 1 and we upgraded 4 levels and downgraded it 3 times, and leveled up 2 times.
        //But max reached level should return 5. We keep track of max reached level too.
        assertEquals(5, level.getMaxLevel());
    }


    @Test
    public void resetLevelTest() {
        Level level = new Level(50);
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.resetLevel();
        assertEquals(1, level.getLevelNumber());
    }


    @Test
    public void resetLevelMaxReachedTest() {
        Level level = new Level(50);
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.resetLevel();
        assertEquals(1, level.getMaxLevel());
    }


    @Test
    public void canLevelUpTest() {
        Level level = new Level(50);
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.downgradeLevel();
        assertTrue(level.canLevelUp());
    }


    @Test
    public void canLevelDownTest() {
        Level level = new Level(50);
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.upgradeLevel();
        level.downgradeLevel();
        level.downgradeLevel();
        level.downgradeLevel();
        level.downgradeLevel();
        assertFalse(level.canLevelDown());
    }


    @Test
    public void addPointTest() {
        Level level = new Level(50);
        level.addPoint(10);
        level.addPoint(15);
        assertEquals(25, level.getCurrentPoint());
    }


    @Test
    public void addCountTest() {
        Level level = new Level(50);
        level.addCount(2);
        level.addCount(5);
        assertEquals(7, level.getMoveCount());
    }



    @Test
    public void isLevelFinishedTest() {
        Level level = new Level(50);
        level.addPoint(27);
        level.addPoint(23);
        assertTrue(level.completed());
    }

}

