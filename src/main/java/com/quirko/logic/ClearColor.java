package com.quirko.logic;

public final class ClearColor {

    private final int[][] newMatrix;
    private final int scoreBonus;

    public ClearColor(int[][] newMatrix, int scoreBonus) {
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    public int getScoreBonus() {
        return scoreBonus;
    }
}
