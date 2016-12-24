package ru.tisov.denis.life.service;

import junit.framework.Assert;
import org.junit.Test;
import ru.tisov.denis.life.domain.Life;

public class LifeOrchestratorTest {

    @Test
    public void testNewLife() throws Exception {
        LifeOrchestrator lifeOrchestrator = new LifeOrchestrator();

        Life life = new Life(new boolean[][]{
                {false, false, false, false},
                {false, true, false, false},
                {false, true, false, false},
                {false, true, false, false},
                {false, false, false, false}
        });

        System.out.println(life);

        Life newLife = lifeOrchestrator.newLife(life);

        System.out.println(newLife);

        Assert.assertEquals(new Life(new boolean[][]{
                {false, false, false, false},
                {false, false, false, false},
                {true, true, true, false},
                {false, false, false, false},
                {false, false, false, false}
        }), newLife);
    }

}