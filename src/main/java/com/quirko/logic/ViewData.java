package com.quirko.logic;

public final class ViewData {

    private boolean DEBUG = true;

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData0;
    private final int[][] nextBrickData1;
    private final int[][] nextBrickData2;
    private final int[][] nextBrickData3;


    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData0, int[][] nextBrickData1, int[][] nextBrickData2, int[][] nextBrickData3) {
        if (DEBUG)  System.out.println("ViewData.ViewData()");
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData0 = nextBrickData0;
        this.nextBrickData1 = nextBrickData1;
        this.nextBrickData2 = nextBrickData2;
        this.nextBrickData3 = nextBrickData3;
    }

    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData0);
    }

    public int[][] getNextBrickDataAt(int index) {
        if (index == 0)
            return MatrixOperations.copy(nextBrickData0);
        if (index == 1)
            return MatrixOperations.copy(nextBrickData1);
        if (index == 2)
            return MatrixOperations.copy(nextBrickData2);
        if (index == 3)
            return MatrixOperations.copy(nextBrickData3);
        else
            return null;
    }



}
