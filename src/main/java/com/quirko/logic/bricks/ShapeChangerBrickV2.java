package com.quirko.logic.bricks;

import com.quirko.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

final class ShapeChangerBrickV2 implements Brick {

    public boolean isSuper=true;

    public List<int[][]> brickMatrix = new ArrayList<>();

    public ShapeChangerBrickV2() {
            brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 8, 0, 0},
                {0, 8, 8, 0},
                {0, 0, 8, 8}
            });
        
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

}
