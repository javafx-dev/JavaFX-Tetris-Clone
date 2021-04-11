package com.quirko.logic.bricks;

import com.quirko.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

final class ShapeChangerBrick implements Brick {

    public boolean isSuper=true;

    public List<int[][]> brickMatrix = new ArrayList<>();

    public ShapeChangerBrick() {
            brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 8, 8, 8},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            });
        
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

}
