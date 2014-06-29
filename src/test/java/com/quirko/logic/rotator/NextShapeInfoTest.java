package com.quirko.logic.rotator;

import org.junit.Assert;
import org.junit.Test;

public class NextShapeInfoTest {

    @Test
    public void testGetShape() throws Exception {
        int[][] shape = new int[][]{{1, 2, 3}, {1, 2, 3}};
        int position = 3;
        NextShapeInfo nextShapeInfo = new NextShapeInfo(shape, position);
        nextShapeInfo.getShape()[0][0] = 23;
        Assert.assertEquals(1, nextShapeInfo.getShape()[0][0]);
    }

    @Test
    public void testGetPosition() throws Exception {

    }
}