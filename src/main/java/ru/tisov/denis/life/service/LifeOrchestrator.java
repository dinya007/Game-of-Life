package ru.tisov.denis.life.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.routing.RoundRobinPool;
import akka.util.Timeout;
import ru.tisov.denis.life.actor.NewGenerationActor;
import ru.tisov.denis.life.domain.Life;
import ru.tisov.denis.life.message.NewLifeRq;
import ru.tisov.denis.life.message.NewLifeRs;
import scala.compat.java8.FutureConverters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class LifeOrchestrator {

    private static final int VIRTUAL_CORES = Runtime.getRuntime().availableProcessors();

    private Timeout timeout = new Timeout(1, TimeUnit.SECONDS);
    private ActorSystem system = ActorSystem.create("game_of_life");
    private ActorRef router;

    public LifeOrchestrator() {
        router = system.actorOf(Props
                .create(NewGenerationActor.class)
                .withRouter(new RoundRobinPool(VIRTUAL_CORES))
                .withDispatcher("game_of_life_dispatcher"));
    }

    public Life newLife(Life life) {
        int rowsPerActor = life.rowNum() / VIRTUAL_CORES;

        List<Future<Object>> futures = new ArrayList<>();

        for (int i = 0; i < VIRTUAL_CORES; i++) {
            if (i == VIRTUAL_CORES - 1) {
                futures.add((CompletableFuture) FutureConverters.toJava(Patterns.ask(router, new NewLifeRq(life, i * rowsPerActor, life.rowNum()), timeout)));
                continue;
            }
            futures.add((CompletableFuture) FutureConverters.toJava(Patterns.ask(router, new NewLifeRq(life, i * rowsPerActor, i * rowsPerActor + rowsPerActor), timeout)));
        }

        boolean[][] result = new boolean[life.rowNum()][life.columnNum()];
        for (Future<Object> future : futures) {
            try {
                NewLifeRs newLifeRs = (NewLifeRs) future.get();
                System.arraycopy(newLifeRs.result, 0, result, newLifeRs.startRow, newLifeRs.result.length);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }


        return new Life(result);
    }

    public void stop() {
        system.shutdown();
    }

}
