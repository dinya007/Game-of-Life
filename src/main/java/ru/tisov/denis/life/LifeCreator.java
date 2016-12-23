package ru.tisov.denis.life;

import java.util.Random;

public class LifeCreator {

    private static final Random random = new Random();

    public static boolean[][] generate(int m) {
        boolean[][] result = new boolean[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = random.nextBoolean();
            }
        }
        return result;
    }

}