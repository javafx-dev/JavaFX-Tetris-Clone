package com.quirko.logic.bricks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBrickGenerator implements BrickGenerator {

    private boolean DEBUG = false;

    private final List<Brick> brickList = new ArrayList<>();

    private final List<Brick> nextBricks = new ArrayList<>();

    public RandomBrickGenerator() {
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));

        if (DEBUG)  System.out.println("brickList size = " + brickList.size());
        if (DEBUG)  System.out.println("nextBricks size = " + nextBricks.size());
    }

    @Override
    public Brick getBrick() {
        if (DEBUG)  System.out.println("RandomBrickGenerator.getBrick()");
        if (DEBUG)  System.out.println("brickList size = " + brickList.size());
        if (DEBUG)  System.out.println("nextBricks size = " + nextBricks.size());
        if (nextBricks.size() <= 4) {
            if (DEBUG)  System.out.println("RandomBrickGenerator.getBrick() -> adding more bricks");
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
        if (DEBUG)  System.out.println("RandomBrickGenerator.getBrick()2");
        return nextBricks.remove(0);
    }

    @Override
    public Brick getNextBrick() {
        if (DEBUG)  System.out.println("RandomBrickGenerator.getNextBrick()");
        return nextBricks.get(0);
    }


    // accepts a value between 0 and 3
    public Brick getNextBrickAt(int index)
    {
        if (DEBUG)  System.out.println("RandomBrickGenerator.getNextBrickAt()");
        return nextBricks.get(index);
    }
}
