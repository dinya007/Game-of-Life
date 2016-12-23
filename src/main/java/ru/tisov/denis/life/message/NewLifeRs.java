package ru.tisov.denis.life.message;

import java.io.Serializable;

public class NewLifeRs implements Serializable {

    public final int startRow;
    public final int finishRow;
    public final boolean[][] result;

    public NewLifeRs(int startRow, int finishRow, boolean[][] result) {
        this.startRow = startRow;
        this.finishRow = finishRow;
        this.result = result;
    }
}
