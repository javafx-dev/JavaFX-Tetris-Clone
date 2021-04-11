package com.quirko.logic.bricks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.quirko.logic.Sound;

public class RandomBrickGenerator implements BrickGenerator {

    private final List<Brick> brickList;

    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());brickList.add(new IBrick());
        brickList.add(new JBrick());brickList.add(new JBrick());
        brickList.add(new LBrick());brickList.add(new LBrick());
        brickList.add(new OBrick());brickList.add(new OBrick());
        brickList.add(new SBrick());brickList.add(new SBrick());
        brickList.add(new TBrick());brickList.add(new TBrick());
        brickList.add(new ZBrick());brickList.add(new ZBrick());
        brickList.add(new ShapeChangerBrick());
        brickList.add(new ShapeChangerBrickV2());
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
    }

    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 1) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
        Brick temp = nextBricks.poll();
        if(temp.getClass().getName().equals("com.quirko.logic.bricks.ShapeChangerBrick"))
        Sound.PlaySound("src/main/resources/sounds/tetrisSpecialSound.wav");
        else if(temp.getClass().getName().equals("com.quirko.logic.bricks.ShapeChangerBrickV2"))
        Sound.PlaySound("src/main/resources/sounds/tetrisSpecialSoundTwo.wav");
        
        return temp;
    }

    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
}
