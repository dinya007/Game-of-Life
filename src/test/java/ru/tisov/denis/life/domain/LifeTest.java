package ru.tisov.denis.life.domain;


import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class LifeTest {

    private final boolean[][] lifeArray = {
            {false, true, false},
            {true, false, true},
            {true, false, false},
    };

    @Test
    public void testAddFirstAndLastColumn() throws Exception {
        Life life = new Life(lifeArray);

        boolean[] result = life.addFirstAndLastColumnToRow(new boolean[]{true, false, true});
        Assert.assertTrue(Arrays.equals(new boolean[]{true, true, false, true, true}, result));

        result = life.addFirstAndLastColumnToRow(new boolean[]{true, false, false});
        Assert.assertTrue(Arrays.equals(result, new boolean[]{false, true, false, false, true}));
    }

    @Test
    public void testCreateLifeWithEdges() throws Exception {
        Life life = new Life(lifeArray);

        boolean[][] result = life.createLifeWithEdges(lifeArray);

        Assert.assertTrue(Arrays.deepEquals(result, new boolean[][]{
                {false, true, false, false, true},
                {false, false, true, false, false},
                {true, true, false, true, true},
                {false, true, false, false, true},
                {false, false, true, false, false}
        }));
    }


}