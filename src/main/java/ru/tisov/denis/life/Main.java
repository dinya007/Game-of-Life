package ru.tisov.denis.life;

public class Main {

    private static final int SIZE = 1000;
    private static final int GENERATIONS = 3000;

    public static void main(String[] args) {
        LifeOrchestrator lifeOrchestrator = new LifeOrchestrator();

        Life life = new Life(LifeCreator.generate(SIZE));

        System.out.println(life);

        long startTime = System.nanoTime();
        for (int gen = 0; gen < GENERATIONS; gen++) {
            life = lifeOrchestrator.newLife(life);
        }
        long time = System.nanoTime() - startTime;

        System.out.println(life);

        System.out.println("Time: " + time);

        lifeOrchestrator.stop();
    }


}
