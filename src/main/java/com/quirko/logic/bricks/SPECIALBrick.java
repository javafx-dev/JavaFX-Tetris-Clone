package com.quirko.logic.bricks;

import com.quirko.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

final class SPECIALBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public SPECIALBrick() {
        brickMatrix.add(new int[][]{
                {8, 8, 8, 0},
                {8, 9, 8, 0},
                {8, 8, 8, 0},
                {0, 0, 0, 0},
        });
         brickMatrix.add(new int[][]{
                {8, 8, 8, 0},
                {8, 1, 8, 0},
                {8, 8, 8, 0},
                {0, 0, 0, 0},
        });
        brickMatrix.add(new int[][]{
            {8, 8, 8, 0},
            {8, 3, 8, 0},
            {8, 8, 8, 0},
            {0, 0, 0, 0},
        }); 
        brickMatrix.add(new int[][]{
            {8, 8, 8, 0},
            {8, 5, 8, 0},
            {8, 8, 8, 0},
            {0, 0, 0, 0},
         });
        brickMatrix.add(new int[][]{
            {8, 8, 8, 0},
            {8, 7, 8, 0},
            {8, 8, 8, 0},
            {0, 0, 0, 0},
         });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

}
