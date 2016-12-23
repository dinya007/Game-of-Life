package ru.tisov.denis.life.actor;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;
import ru.tisov.denis.life.message.NewLifeRq;
import ru.tisov.denis.life.message.NewLifeRs;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class NewGenerationActor extends AbstractActor {

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder.match(NewLifeRq.class, rq -> {
            boolean[][] result = rq.currentLife.newGenerationForRows(rq.startRow, rq.finishRow);
            sender().tell(new NewLifeRs(rq.startRow, rq.finishRow, result), self());
        })
                .matchAny(x -> sender().tell(new Status.Failure(new RuntimeException("Unknown message")), self()))
                .build();
    }
}
