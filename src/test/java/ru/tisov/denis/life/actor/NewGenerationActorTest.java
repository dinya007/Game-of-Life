package ru.tisov.denis.life.actor;

import akka.actor.Actor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActor;
import akka.testkit.TestActorRef;
import org.junit.Assert;
import org.junit.Test;
import ru.tisov.denis.life.Life;

import java.util.Arrays;

public class NewGenerationActorTest {

    ActorSystem system = ActorSystem.create();

    private final boolean[][] lifeArray = {
            {false, true, false},
            {true, false, true},
            {true, false, false},
    };

    @Test
    public void testCount() throws Exception {
        TestActorRef<NewGenerationActor> actor = TestActorRef.
                create(system, Props.create(NewGenerationActor.class));

        Life life = new Life(lifeArray);

        int result = actor.underlyingActor().count(life, 2, 2);

        Assert.assertEquals(4, result);
    }

    @Test
    public void testNewGenerationForRows() {
        TestActorRef<NewGenerationActor> actor = TestActorRef.
                create(system, Props.create(NewGenerationActor.class));

        Life life = new Life(new boolean[][]{
                {false, false, false},
                {true, false, false},
                {true, true, false}
        });

        boolean[][] result = actor.underlyingActor().newGenerationForRows(life, 1, 2);

        Assert.assertTrue(Arrays.deepEquals(result, new boolean[][]{
                {true, true, true}
        }));
    }

}