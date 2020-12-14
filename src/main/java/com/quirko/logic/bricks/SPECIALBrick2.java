package com.quirko.logic.bricks;

import com.quirko.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

final class SPECIALBrick2 implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public SPECIALBrick2() {
        brickMatrix.add(new int[][]{
                {8, 8, 8, 0},
                {8, 9, 8, 0},
                {8, 8, 8, 0},
                {0, 0, 0, 0},
        });
         brickMatrix.add(new int[][]{
                {8, 8, 8, 0},
                {8, 2, 8, 0},
                {8, 8, 8, 0},
                {0, 0, 0, 0},
        });
        brickMatrix.add(new int[][]{
            {8, 8, 8, 0},
            {8, 4, 8, 0},
            {8, 8, 8, 0},
            {0, 0, 0, 0},
        }); 
        brickMatrix.add(new int[][]{
            {8, 8, 8, 0},
            {8, 6, 8, 0},
            {8, 8, 8, 0},
            {0, 0, 0, 0},
         });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

}
