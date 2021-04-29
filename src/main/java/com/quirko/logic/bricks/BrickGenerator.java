package com.quirko.logic.bricks;

public interface BrickGenerator {

    Brick getBrick();

    Brick getNextBrick();

    public Brick peekNextBrickAt(int index);
}
