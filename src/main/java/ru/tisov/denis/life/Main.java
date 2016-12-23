package ru.tisov.denis.life;

import ru.tisov.denis.life.base.SimpleLife;

public class Main {

    private static final int SIZE = 1000;
    private static final int GENERATIONS = 1000;

    public static void main(String[] args) throws InterruptedException {

        SimpleLife earth = new SimpleLife(LifeCreator.generate(SIZE));
        long startTime = System.nanoTime();
        for (int i = 0; i < GENERATIONS; i++) {
            earth.nextGeneration();
        }
        long baseDuration = System.nanoTime() - startTime;
        System.out.println("Single thread realization time: " + baseDuration);


        LifeOrchestrator lifeOrchestrator = new LifeOrchestrator();

        Life life = new Life(LifeCreator.generate(SIZE));

        startTime = System.nanoTime();
        for (int i = 0; i < GENERATIONS; i++) {
            life = lifeOrchestrator.newLife(life);
        }

        long actorsDuration = System.nanoTime() - startTime;
        System.out.println("Actor's time: " + actorsDuration);

        System.out.println("Profit: " + ((double) baseDuration / (double) actorsDuration));

        lifeOrchestrator.stop();
    }


}
